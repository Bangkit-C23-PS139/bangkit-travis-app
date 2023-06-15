package com.rickyslash.travis.ui.main.pages.bonding

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rickyslash.travis.R
import com.rickyslash.travis.api.response.BondingListDataItem
import com.rickyslash.travis.data.LoadingStateAdapter
import com.rickyslash.travis.databinding.FragmentBondingBinding
import com.rickyslash.travis.helper.ViewModelFactory
import com.rickyslash.travis.ui.login.LoginActivity
import com.rickyslash.travis.ui.main.pages.bonding.bondingdetail.BondingDetailActivity

class BondingFragment : Fragment() {

    private lateinit var bondingViewModel: BondingViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentBondingBinding.inflate(layoutInflater)
    }

    private var isLoadingObserver: Observer<Boolean>? = null
    private var isErrorObserver: Observer<Boolean>? = null
    private var responseMessageObserver: Observer<String?>? = null
    private var joinResponseMessageObserver: Observer<String?>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupViewModel()
        setupView()
        setupAction()
        return binding.root
    }

    private fun setupView() {
        setupRV()
        binding.tvBondingHeaderTitle.text = bondingViewModel.getPreferences().currentLocation

        binding.btnBondingRequest.setOnClickListener {
            val navIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.dummy_wa)))
            startActivity(navIntent)
        }

        if (bondingViewModel.getPreferences().isLogin) {
            Glide.with(this)
                .load(bondingViewModel.getPreferences().profilePhoto)
                .placeholder(R.drawable.dummy_home_upload)
                .into(binding.ivAvatar)
        } else {
            binding.ivAvatar.setImageResource(R.drawable.dummy_home_upload)
        }
    }

    private fun setupViewModel() {
        bondingViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(requireActivity().application))[BondingViewModel::class.java]
        observeLoading()
    }

    private fun setupAction() {
        setBondingData()
    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvBonding.layoutManager = layoutManager
    }

    private fun showBondingDetails(data: BondingListDataItem) {
        val intent = Intent(requireActivity(), BondingDetailActivity::class.java)
        intent.putExtra(BondingDetailActivity.EXTRA_BONDING_ITEM, data)
        startActivity(intent)
    }

    private fun setBondingData() {
        val bondingAdapter = BondingAdapter()
        binding.rvBonding.adapter = bondingAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { bondingAdapter.retry() }
        )

        val currentCity = bondingViewModel.getPreferences().currentLocation
        if (!currentCity.equals("YOGYAKARTA", ignoreCase = true) && !currentCity.equals("JAKARTA PUSAT", ignoreCase = true)) {
            Toast.makeText(requireActivity(), R.string.info_city_unavailable, Toast.LENGTH_LONG).show()
        }

        bondingViewModel.bonding.observe(requireActivity()) {
            bondingAdapter.submitData(lifecycle, it)
        }

        bondingAdapter.setOnItemClickCallback(object : BondingAdapter.OnItemClickCallback {
            override fun onItemClicked(data: BondingListDataItem) {
                showBondingDetails(data)
            }
        })

        bondingAdapter.setOnButtonJoinClickCallback(object : BondingAdapter.OnButtonJoinClickCallback {
            override fun onButtonJoinClicked(bondingId: String) {
                joinBonding(bondingId)
            }
        })
    }

    private fun joinBonding(bondingId: String) {
        if (bondingViewModel.getPreferences().isLogin) {
            joinResponseMessageObserver = Observer { joinResponseMessage ->
                bondingViewModel.joinBonding(bondingId)
                if (joinResponseMessage != null) {
                    Toast.makeText(requireContext(), bondingViewModel.joinResponseMessage.value, Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvBonding.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun observeLoading() {
        isLoadingObserver = Observer { showLoading(it) }
        isLoadingObserver?.let {
            bondingViewModel.isLoading.observe(requireActivity(), it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoadingObserver?.let(bondingViewModel.isLoading::removeObserver)
        isErrorObserver?.let(bondingViewModel.isError::removeObserver)
        responseMessageObserver?.let(bondingViewModel.responseMessage::removeObserver)
        joinResponseMessageObserver?.let(bondingViewModel.joinResponseMessage::removeObserver)
    }
}