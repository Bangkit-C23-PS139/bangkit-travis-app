package com.rickyslash.travis.ui.main.pages.bonding

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
import com.rickyslash.travis.api.response.BondingItem
import com.rickyslash.travis.databinding.FragmentBondingBinding
import com.rickyslash.travis.ui.main.pages.bonding.bondingdetail.BondingDetailActivity

class BondingFragment : Fragment() {

    private lateinit var bondingViewModel: BondingViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentBondingBinding.inflate(layoutInflater)
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
        bondingViewModel = ViewModelProvider(requireActivity())[BondingViewModel::class.java]
        setupRecyclerViewData()
        observeLoading()
    }

    private fun setupRecyclerViewData() {
        bondingViewModel.getBondingList()
        isErrorObserver = Observer { isError ->
            if (!isError) {
                bondingViewModel.listBondingItem.observe(requireActivity()) {
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

    private fun setBondingData(bondingData: List<BondingItem>) {
        val bondingAdapter = BondingAdapter(bondingData)
        binding.rvBonding.adapter = bondingAdapter

        bondingAdapter.setOnItemClickCallback(object : BondingAdapter.OnItemClickCallback {
            override fun onItemClicked(data: BondingItem) {
                showBondingDetails(data)
            }
        })
    }

    private fun showBondingDetails(data: BondingItem) {
        val intent = Intent(requireActivity(), BondingDetailActivity::class.java)
        intent.putExtra(BondingDetailActivity.EXTRA_BONDING_ITEM, data)
        startActivity(intent)
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
    }
}