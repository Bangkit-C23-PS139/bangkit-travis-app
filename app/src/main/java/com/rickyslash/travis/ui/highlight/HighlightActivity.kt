package com.rickyslash.travis.ui.highlight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyslash.travis.R
import com.rickyslash.travis.api.dummy.dummyresponse.HighlightItem
import com.rickyslash.travis.databinding.ActivityHighlightBinding
import com.rickyslash.travis.ui.highlight.highlightdetail.HighlightDetailActivity

class HighlightActivity : AppCompatActivity() {

    private lateinit var highlightViewModel: HighlightViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityHighlightBinding.inflate(layoutInflater)
    }

    private var isLoadingObserver: Observer<Boolean>? = null
    private var isErrorObserver: Observer<Boolean>? = null
    private var responseMessageObserver: Observer<String?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        setupRV()
    }

    private fun setupViewModel() {
        highlightViewModel = ViewModelProvider(this)[HighlightViewModel::class.java]
        setupRecyclerViewData()
        observeLoading()
    }

    private fun setupAction() {
        binding.appbarTop.setNavigationOnClickListener {
            this.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerViewData() {
        highlightViewModel.getHighlights()
        isErrorObserver = Observer { isError ->
            if (!isError) {
                highlightViewModel.listHighlightItem.observe(this) {
                    if (it.isNotEmpty()) {
                        setHighlightData(it)
                    } else {
                        Toast.makeText(this, getString(R.string.label_highlight_no_data), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        responseMessageObserver = Observer { responseMessage ->
            if (responseMessage != null && highlightViewModel.isError.value == true) {
                Toast.makeText(this, responseMessage, Toast.LENGTH_SHORT).show()
            }
        }

        isErrorObserver?.let { highlightViewModel.isError.observe(this, it) }
        responseMessageObserver?.let { highlightViewModel.responseMessage.observe(this, it) }
    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvHighlight.layoutManager = layoutManager
    }

    private fun setHighlightData(highlightData: List<HighlightItem>) {
        val highlightAdapter = HighlightAdapter(highlightData)
        binding.rvHighlight.adapter = highlightAdapter

        highlightAdapter.setOnItemClickCallback(object : HighlightAdapter.OnItemClickCallback {
            override fun onItemClicked(data: HighlightItem) {
                showHighlightDetails(data)
            }
        })
    }

    private fun showHighlightDetails(data: HighlightItem) {
        val intent = Intent(this@HighlightActivity, HighlightDetailActivity::class.java)
        intent.putExtra(HighlightDetailActivity.EXTRA_HIGHLIGHT_ITEM, data)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvHighlight.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun observeLoading() {
        isLoadingObserver = Observer { showLoading((it)) }
        isLoadingObserver?.let {
            highlightViewModel.isLoading.observe(this, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isLoadingObserver?.let(highlightViewModel.isLoading::removeObserver)
        isErrorObserver?.let(highlightViewModel.isError::removeObserver)
        responseMessageObserver?.let(highlightViewModel.responseMessage::removeObserver)
    }

}