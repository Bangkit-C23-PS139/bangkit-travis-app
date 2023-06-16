package com.rickyslash.travis.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rickyslash.travis.R
import com.rickyslash.travis.api.entity.BondingData
import com.rickyslash.travis.databinding.ActivityProfileBinding
import com.rickyslash.travis.helper.ViewModelFactory
import com.rickyslash.travis.ui.main.MainActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private var isErrorObserver: Observer<Boolean>? = null
    private var responseMessageObserver: Observer<String?>? = null
    private var isLoadingObserver: Observer<Boolean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        setupAction()
    }

    private fun setupView() {
        setupRV()
        if (profileViewModel.getPreferences().isLogin) {
            binding.tvProfileName.text = profileViewModel.getPreferences().name
            Glide.with(this)
                .load(profileViewModel.getPreferences().profilePhoto)
                .placeholder(R.drawable.dummy_home_upload)
                .into(binding.ivAvatar)
        } else {
            binding.ivAvatar.setImageResource(R.drawable.dummy_home_upload)
        }
    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(this, ViewModelFactory(application))[ProfileViewModel::class.java]
        setupRecyclerView()
        observeLoading()
    }

    private fun setupAction() {
        binding.appbarTop.setNavigationOnClickListener {
            this.onBackPressedDispatcher.onBackPressed()
        }
        binding.appbarTop.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_profile_logout -> {
                    val intent = Intent(this@ProfileActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                    profileViewModel.logout()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        profileViewModel.getSelfBondingItems()
        isErrorObserver = Observer { isError ->
            if (!isError) {
                profileViewModel.listSelfUserBondingItem.observe(this) {
                    if (it.isNotEmpty()) {
                        setSelfUserBondingData(it)
                    }
                }
            }
        }
        responseMessageObserver = Observer { responseMessage ->
            if (responseMessage != null && profileViewModel.isError.value == true) {
                Toast.makeText(this, responseMessage, Toast.LENGTH_SHORT).show()
            }
        }
        isErrorObserver?.let { profileViewModel.isError.observe(this, it) }
        responseMessageObserver?.let { profileViewModel.responseMessage.observe(this, it) }
    }

    private fun setSelfUserBondingData(selfUserBondingData: List<BondingData>) {
        val profileAdapter = ProfileAdapter(selfUserBondingData)
        binding.rvJoinedProfile.adapter = profileAdapter
    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvJoinedProfile.layoutManager = layoutManager
    }

    private fun observeLoading() {
        isLoadingObserver = Observer { showLoading(it) }
        isLoadingObserver?.let {
            profileViewModel.isLoading.observe(this, it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvJoinedProfile.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        isErrorObserver?.let(profileViewModel.isError::removeObserver)
        responseMessageObserver?.let(profileViewModel.responseMessage::removeObserver)
        isLoadingObserver?.let(profileViewModel.isLoading::removeObserver)
    }

}