package com.rickyslash.travis.ui.main.pages.service

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyslash.travis.R
import com.rickyslash.travis.api.response.ServiceItem
import com.rickyslash.travis.databinding.FragmentServiceBinding
import com.rickyslash.travis.ui.servicedetail.ServiceDetailActivity

class ServiceFragment : Fragment() {

    private lateinit var serviceViewModel: ServiceViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentServiceBinding.inflate(layoutInflater)
    }

    private var isLoadingObserver: Observer<Boolean>? = null
    private var isErrorObserver: Observer<Boolean>? = null
    private var responseMessageObserver: Observer<String?>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupView()
        setupViewModel()
        return binding.root
    }

    private fun setupView() {
        setupRV()
    }

    private fun setupViewModel() {
        serviceViewModel = ViewModelProvider(requireActivity())[ServiceViewModel::class.java]
        setupRecyclerViewData()
        observeLoading()
    }

    private fun setupRecyclerViewData() {
        serviceViewModel.getServices()
        isErrorObserver = Observer { isError ->
            if (!isError) {
                serviceViewModel.listServiceItem.observe(requireActivity()) {
                    if (it.isNotEmpty()) {
                        setServiceData(it)
                    } else {
                        Toast.makeText(requireActivity(), getString(R.string.label_tplan_no_data), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        responseMessageObserver = Observer { responseMessage ->
            if (responseMessage != null && serviceViewModel.isError.value == true) {
                Toast.makeText(requireActivity(), responseMessage, Toast.LENGTH_SHORT).show()
            }
        }

        isErrorObserver?.let { serviceViewModel.isError.observe(requireActivity(), it) }
        responseMessageObserver?.let { serviceViewModel.responseMessage.observe(requireActivity(), it) }
    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvService.layoutManager = layoutManager
    }

    private fun setServiceData(serviceData: List<ServiceItem>) {
        val serviceAdapter = ServiceAdapter(serviceData)
        binding.rvService.adapter = serviceAdapter

        serviceAdapter.setOnItemClickCallback(object : ServiceAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ServiceItem) {
                showServiceDetails(data)
            }
        })
    }

    private fun showServiceDetails(data: ServiceItem) {
        val intent = Intent(requireActivity(), ServiceDetailActivity::class.java)
        intent.putExtra(ServiceDetailActivity.EXTRA_SERVICE_ITEM, data)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvService.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun observeLoading() {
        isLoadingObserver = Observer { showLoading(it) }
        isLoadingObserver?.let {
            serviceViewModel.isLoading.observe(requireActivity(), it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoadingObserver?.let(serviceViewModel.isLoading::removeObserver)
        isErrorObserver?.let(serviceViewModel.isError::removeObserver)
        responseMessageObserver?.let(serviceViewModel.responseMessage::removeObserver)
    }
}