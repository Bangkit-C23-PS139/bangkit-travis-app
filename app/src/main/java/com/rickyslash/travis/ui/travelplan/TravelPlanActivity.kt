package com.rickyslash.travis.ui.travelplan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyslash.travis.R
import com.rickyslash.travis.api.response.TravelPlanItem
import com.rickyslash.travis.databinding.ActivityTravelPlanBinding
import com.rickyslash.travis.ui.travelplan.travelplandetail.TravelPlanDetailActivity

class TravelPlanActivity : AppCompatActivity() {

    private lateinit var travelPlanViewModel: TravelPlanViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityTravelPlanBinding.inflate(layoutInflater)
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
        travelPlanViewModel = ViewModelProvider(this)[TravelPlanViewModel::class.java]
        setupRecyclerViewData()
        observeLoading()
    }

    private fun setupAction() {
        binding.appbarTop.setNavigationOnClickListener {
            this.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerViewData() {
        travelPlanViewModel.getTravelPlan()
        isErrorObserver = Observer { isError ->
            if (!isError) {
                travelPlanViewModel.listTravelPlanItem.observe(this) {
                    if (it.isNotEmpty()) {
                        setTravelPlanData(it)
                    } else {
                        Toast.makeText(this, getString(R.string.label_tplan_no_data), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        responseMessageObserver = Observer { responseMessage ->
            if (responseMessage != null && travelPlanViewModel.isError.value == true) {
                Toast.makeText(this, responseMessage, Toast.LENGTH_SHORT).show()
            }
        }

        isErrorObserver?.let { travelPlanViewModel.isError.observe(this, it) }
        responseMessageObserver?.let { travelPlanViewModel.responseMessage.observe(this, it) }
    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvTplan.layoutManager = layoutManager
    }

    private fun setTravelPlanData(travelPlanData: List<TravelPlanItem>) {
        val travelPlanAdapter = TravelPlanAdapter(travelPlanData)
        binding.rvTplan.adapter = travelPlanAdapter

        travelPlanAdapter.setOnItemClickCallback(object : TravelPlanAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TravelPlanItem) {
                showTravelPlanDetails(data)
            }
        })
    }

    private fun showTravelPlanDetails(data: TravelPlanItem) {
        val intent = Intent(this@TravelPlanActivity, TravelPlanDetailActivity::class.java)
        intent.putExtra(TravelPlanDetailActivity.EXTRA_TRAVEL_PLAN_ITEM, data)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvTplan.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun observeLoading() {
        isLoadingObserver = Observer { showLoading(it) }
        isLoadingObserver?.let {
            travelPlanViewModel.isLoading.observe(this, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isLoadingObserver?.let(travelPlanViewModel.isLoading::removeObserver)
        isErrorObserver?.let(travelPlanViewModel.isError::removeObserver)
        responseMessageObserver?.let(travelPlanViewModel.responseMessage::removeObserver)
    }

}