package com.rickyslash.travis.ui.main.pages.service

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rickyslash.travis.R
import com.rickyslash.travis.api.dummy.dummyresponse.ServiceItem
import com.rickyslash.travis.data.LoadingStateAdapter
import com.rickyslash.travis.databinding.FragmentServiceBinding
import com.rickyslash.travis.helper.ViewModelFactory
import com.rickyslash.travis.helper.getFirstWord
import com.rickyslash.travis.ui.main.pages.service.servicedetail.ServiceDetailActivity

class ServiceFragment : Fragment() {

    private lateinit var serviceViewModel: ServiceViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentServiceBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupViewModel()
        setupView()
        setupAction()
        return binding.root
    }

    private fun setupView() {
        setupRV()
        binding.tvServiceHeaderTitle.text = serviceViewModel.getPreferences().currentLocation

        binding.btnServiceRequest.setOnClickListener {
            val navIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.dummy_wa)))
            startActivity(navIntent)
        }

        if (serviceViewModel.getPreferences().isLogin) {
            Glide.with(this)
                .load(serviceViewModel.getPreferences().profilePhoto)
                .placeholder(R.drawable.dummy_home_upload)
                .into(binding.ivAvatar)
        } else {
            binding.ivAvatar.setImageResource(R.drawable.dummy_home_upload)
        }
    }

    private fun setupViewModel() {
        serviceViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(requireActivity().application))[ServiceViewModel::class.java]
    }

    private fun setupAction() {
        setServiceData()
    }

    private fun showServiceDetails(data: ServiceItem) {
        val intent = Intent(requireActivity(), ServiceDetailActivity::class.java)
        intent.putExtra(ServiceDetailActivity.EXTRA_SERVICE_ITEM, data)
        startActivity(intent)
    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvService.layoutManager = layoutManager
    }

    private fun setServiceData() {
        val serviceAdapter = ServiceAdapter()
        binding.rvService.adapter = serviceAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { serviceAdapter.retry() }
        )

        val currentCity = serviceViewModel.getPreferences().currentLocation
        if (!currentCity.equals("YOGYAKARTA", ignoreCase = true) && !currentCity.equals("JAKARTA PUSAT", ignoreCase = true)) {
            Toast.makeText(requireActivity(), R.string.info_city_unavailable, Toast.LENGTH_LONG).show()
        }

        serviceViewModel.service.observe(requireActivity()) {
            serviceAdapter.submitData(lifecycle, it)
        }

        serviceAdapter.setOnItemClickCallback(object : ServiceAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ServiceItem) {
                showServiceDetails(data)
            }
        })
    }
}