package com.rickyslash.travis.ui.settings.preference

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.rickyslash.travis.R
import com.rickyslash.travis.databinding.ActivityTravelPreferenceBinding
import com.rickyslash.travis.helper.GridSpacingItemDecoration
import com.rickyslash.travis.helper.ViewModelFactory
import com.rickyslash.travis.model.SignupModel
import com.rickyslash.travis.ui.main.MainActivity

class TravelPreferenceActivity : AppCompatActivity() {

    private lateinit var travelPreferenceViewModel: TravelPreferenceViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityTravelPreferenceBinding.inflate(layoutInflater)
    }

    private lateinit var travelPreferenceAdapter: TravelPreferenceAdapter
    private var selectedPreferences = mutableListOf<String>()

    private var signupData: SignupModel? = null

    private var isErrorObserver: Observer<Boolean>? = null
    private var responseMessageObserver: Observer<String?>? = null
    private var isLoadingObserver: Observer<Boolean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        getSignupIntentExtra()
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
        binding.appbarTop.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_preference -> {
                    isLoadingObserver = Observer { showLoading(it) }
                    isLoadingObserver?.let { travelPreferenceViewModel.isLoading.observe(this, it) }

                    selectedPreferences = if (::travelPreferenceAdapter.isInitialized) travelPreferenceAdapter.getCheckedPreference() else mutableListOf()
                    signupData?.travelPreferences = selectedPreferences

                    dataSignupOrUpdateExecution(signupData, selectedPreferences)

                    isErrorObserver = Observer { isError ->
                        if (!isError) {
                            Toast.makeText(this, R.string.label_process_success, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@TravelPreferenceActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                    }
                    responseMessageObserver = Observer { responseMessage ->
                        if (responseMessage != null && travelPreferenceViewModel.isError.value == true) {
                            Toast.makeText(this, responseMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                    isErrorObserver?.let { travelPreferenceViewModel.isError.observe(this, it) }
                    responseMessageObserver?.let { travelPreferenceViewModel.responseMessage.observe(this, it) }
                    true
                }
                else -> false
            }
        }
    }

    private fun getSignupIntentExtra() {
        val data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_SIGNUP_ITEM, SignupModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_SIGNUP_ITEM)
        }
        if (data != null) {
            signupData = data
        }
    }

    private fun dataSignupOrUpdateExecution(signupData: SignupModel?, travelPreferenceList: List<String>) {
        if (!travelPreferenceViewModel.getPreferences().isLogin) {
            travelPreferenceViewModel.userSignup(signupData)
        } else {
            travelPreferenceViewModel.updateTravelPreference(travelPreferenceList)
        }
    }

    private fun setupRecyclerViewData() {
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
        travelPreferenceAdapter = TravelPreferenceAdapter(selfPreference, travelPreferenceList)
        binding.rvTpref.adapter = travelPreferenceAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        isErrorObserver?.let(travelPreferenceViewModel.isError::removeObserver)
        responseMessageObserver?.let(travelPreferenceViewModel.responseMessage::removeObserver)
        isLoadingObserver?.let(travelPreferenceViewModel.isLoading::removeObserver)
    }

    companion object {
        const val EXTRA_SIGNUP_ITEM = "extra_signup_item"
    }

}