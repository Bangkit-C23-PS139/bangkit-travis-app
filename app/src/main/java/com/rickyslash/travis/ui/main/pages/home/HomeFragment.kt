package com.rickyslash.travis.ui.main.pages.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.rickyslash.travis.R
import com.rickyslash.travis.databinding.FragmentHomeBinding
import com.rickyslash.travis.helper.getDateToday
import com.rickyslash.travis.ui.highlight.HighlightActivity
import com.rickyslash.travis.ui.travelplan.TravelPlanActivity

class HomeFragment : Fragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setupView()
        setupAction()
        return binding.root
    }

    private fun setupView() {
        binding.tvHeaderDate.text = getDateToday().uppercase()
    }

    private fun setupAction() {
        binding.btnPromptHelp.setOnClickListener { dialogPromptHelp() }
        binding.mcvHighlight.setOnClickListener { startActivity(Intent(requireContext(), HighlightActivity::class.java)) }
        binding.btnPromptSend.setOnClickListener { startActivity(Intent(requireActivity(), TravelPlanActivity::class.java)) }
    }

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