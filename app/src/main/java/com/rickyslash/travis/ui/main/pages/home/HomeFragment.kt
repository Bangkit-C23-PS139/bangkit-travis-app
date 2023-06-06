package com.rickyslash.travis.ui.main.pages.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rickyslash.travis.R
import com.rickyslash.travis.databinding.FragmentHomeBinding
import com.rickyslash.travis.helper.getDateToday

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
        binding.btnPromptHelp.setOnClickListener { dialogPromptHelp() }
    }

    @Suppress("DEPRECATION")
    private fun dialogPromptHelp() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.label_help_prompt_title))
            setView(R.layout.textview_dialog_message)
            setPositiveButton(getString(R.string.label_ok), null)
            create()
            show()
        }
    }

}