package com.rickyslash.travis.ui.main.pages.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rickyslash.travis.R
import com.rickyslash.travis.databinding.FragmentHomeBinding
import com.rickyslash.travis.helper.ViewModelFactory
import com.rickyslash.travis.helper.getDateToday
import com.rickyslash.travis.helper.getFirstWord
import com.rickyslash.travis.ui.highlight.HighlightActivity
import com.rickyslash.travis.ui.login.LoginActivity
import com.rickyslash.travis.ui.settings.preference.TravelPreferenceActivity
import com.rickyslash.travis.ui.travelplan.TravelPlanActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setupViewModel()
        setupView()
        setupAction()
        return binding.root
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(requireActivity().application))[HomeViewModel::class.java]
    }

    private fun setupView() {
        binding.tvHomeHeaderDate.text = getDateToday().uppercase()
        if (homeViewModel.getPreferences().isLogin) {
            binding.tvHomeHeaderTitle.text = getString(R.string.arg_home_name,
                homeViewModel.getPreferences().name?.let { getFirstWord(it) })
            Glide.with(this)
                .load(homeViewModel.getPreferences().profilePhoto)
                .placeholder(R.drawable.dummy_home_upload)
                .into(binding.ivAvatar)
        } else {
            binding.tvHomeHeaderTitle.text = getString(R.string.default_home_name)
            binding.ivAvatar.setImageResource(R.drawable.dummy_home_upload)
        }
    }

    private fun setupAction() {
        binding.btnHomeTpref.setOnClickListener { startActivity(Intent(requireContext(), TravelPreferenceActivity::class.java)) }
        binding.mcvHighlight.setOnClickListener { startActivity(Intent(requireContext(), HighlightActivity::class.java)) }
        binding.btnHomeGenerate.setOnClickListener { startActivity(Intent(requireActivity(), TravelPlanActivity::class.java)) }
        binding.ivAvatar.setOnClickListener { startActivity(Intent(requireContext(), LoginActivity::class.java)) }
        binding.btnHighlight.setOnClickListener { startActivity(Intent(requireContext(), HighlightActivity::class.java)) }
    }

}