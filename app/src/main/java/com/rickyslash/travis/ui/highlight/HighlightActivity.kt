package com.rickyslash.travis.ui.highlight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyslash.travis.R
import com.rickyslash.travis.api.response.HighlightDataItem
import com.rickyslash.travis.data.LoadingStateAdapter
import com.rickyslash.travis.databinding.ActivityHighlightBinding
import com.rickyslash.travis.helper.ViewModelFactory
import com.rickyslash.travis.ui.highlight.highlightdetail.HighlightDetailActivity

class HighlightActivity : AppCompatActivity() {

    private lateinit var highlightViewModel: HighlightViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityHighlightBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        setupAction()
    }

    private fun setupView() {
        setupRV()
        binding.tvHighlightHeaderTitle.text = highlightViewModel.getCurrentCity()
    }

    private fun setupViewModel() {
        highlightViewModel = ViewModelProvider(this, ViewModelFactory(application))[HighlightViewModel::class.java]
    }

    private fun setupAction() {
        setHighlightData()
        binding.appbarTop.setNavigationOnClickListener {
            this.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showHighlightDetails(data: HighlightDataItem) {
        val intent = Intent(this@HighlightActivity, HighlightDetailActivity::class.java)
        intent.putExtra(HighlightDetailActivity.EXTRA_HIGHLIGHT_ITEM, data)
        startActivity(intent)
    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvHighlight.layoutManager = layoutManager
    }

    private fun setHighlightData() {
        val highlightAdapter = HighlightAdapter()
        binding.rvHighlight.adapter = highlightAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { highlightAdapter.retry() }
        )

        val currentCity = highlightViewModel.getCurrentCity()
        if (!currentCity.equals("YOGYAKARTA", ignoreCase = true) && !currentCity.equals("JAKARTA PUSAT", ignoreCase = true)) {
            Toast.makeText(this, R.string.info_city_unavailable, Toast.LENGTH_LONG).show()
        }

        highlightViewModel.highlight.observe(this) {
            highlightAdapter.submitData(lifecycle, it)
        }

        highlightAdapter.setOnItemClickCallback(object : HighlightAdapter.OnItemClickCallback {
            override fun onItemClicked(data: HighlightDataItem) {
                showHighlightDetails(data)
            }
        })
    }
}