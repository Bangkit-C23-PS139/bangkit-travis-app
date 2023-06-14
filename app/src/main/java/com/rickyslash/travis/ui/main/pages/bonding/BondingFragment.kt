package com.rickyslash.travis.ui.main.pages.bonding

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rickyslash.travis.R
import com.rickyslash.travis.api.response.BondingListDataItem
import com.rickyslash.travis.databinding.FragmentBondingBinding
import com.rickyslash.travis.helper.ViewModelFactory
import com.rickyslash.travis.helper.removeKabupatenKota
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

        return binding.root
    }

    private fun setupViewModel() {
        bondingViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(requireActivity().application))[BondingViewModel::class.java]
        setupRV()
        setupRecyclerViewData()
        observeLoading()
    }

    private fun setupView() {
        binding.tvBondingHeaderTitle.text = bondingViewModel.getPreferences().currentLocation
    }

    private fun setupRecyclerViewData() {
        bondingViewModel.getBondingList()
        isErrorObserver = Observer { isError ->
            if (!isError) {
                bondingViewModel.listBondingData.observe(requireActivity()) {
                    if (it.isNotEmpty()) {
                        setBondingData(it)
                    } else {
                        Toast.makeText(requireActivity(), getString(R.string.label_tplan_no_data), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        responseMessageObserver = Observer { responseMessage ->
            if (responseMessage != null && bondingViewModel.isError.value == true) {
                Toast.makeText(requireActivity(), responseMessage, Toast.LENGTH_SHORT).show()
            }
        }

        isErrorObserver?.let { bondingViewModel.isError.observe(requireActivity(), it) }
        responseMessageObserver?.let { bondingViewModel.responseMessage.observe(requireActivity(), it) }
    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvBonding.layoutManager = layoutManager
    }

    private fun setBondingData(bondingData: List<BondingListDataItem>) {
        val bondingAdapter = BondingAdapter(bondingData)
        binding.rvBonding.adapter = bondingAdapter

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

    private fun showBondingDetails(data: BondingListDataItem) {
        val intent = Intent(requireActivity(), BondingDetailActivity::class.java)
        intent.putExtra(BondingDetailActivity.EXTRA_BONDING_ITEM, data)
        startActivity(intent)
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