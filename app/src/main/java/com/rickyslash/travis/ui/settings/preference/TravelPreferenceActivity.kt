package com.rickyslash.travis.ui.settings.preference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.rickyslash.travis.databinding.ActivityTravelPreferenceBinding
import com.rickyslash.travis.helper.GridSpacingItemDecoration
import com.rickyslash.travis.helper.ViewModelFactory

class TravelPreferenceActivity : AppCompatActivity() {

    private lateinit var travelPreferenceViewModel: TravelPreferenceViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityTravelPreferenceBinding.inflate(layoutInflater)
    }

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
        travelPreferenceViewModel = ViewModelProvider(this, ViewModelFactory(application))[TravelPreferenceViewModel::class.java]
        setupRecyclerViewData()
    }

    private fun setupAction() {
        binding.appbarTop.setNavigationOnClickListener {
            this.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerViewData() {
        Log.d("PreferenceActivity", "SetupRecyclerViewData")
        val selfPreferences = travelPreferenceViewModel.getSelfPreferences()
        val travelPreferenceList = travelPreferenceViewModel.getTravelPreferences()
        setTravelPreferenceData(selfPreferences, travelPreferenceList)
    }

    private fun setupRV() {
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvTpref.layoutManager = layoutManager
        binding.rvTpref.addItemDecoration((GridSpacingItemDecoration(32, this)))
    }

    private fun setTravelPreferenceData(selfPreference: List<String>, travelPreferenceList: List<String>) {
        val travelPreferenceAdapter = TravelPreferenceAdapter(selfPreference, travelPreferenceList)
        binding.rvTpref.adapter = travelPreferenceAdapter
    }
}