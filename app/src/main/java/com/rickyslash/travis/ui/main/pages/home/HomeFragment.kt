package com.rickyslash.travis.ui.main.pages.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rickyslash.travis.databinding.FragmentHomeBinding
import com.rickyslash.travis.ui.main.helper.getDateToday

class HomeFragment : Fragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.tvHeaderDate.text = getDateToday().uppercase()
    }

}