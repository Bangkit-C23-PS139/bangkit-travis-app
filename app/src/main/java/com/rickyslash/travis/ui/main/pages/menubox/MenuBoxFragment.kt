package com.rickyslash.travis.ui.main.pages.menubox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rickyslash.travis.R
import com.rickyslash.travis.databinding.FragmentMenuBoxBinding
import com.rickyslash.travis.helper.ViewModelFactory

class MenuBoxFragment : Fragment() {

    private lateinit var menuBoxViewModel: MenuBoxViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentMenuBoxBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setupViewModel()
        setupAction()
        return binding.root
    }

    private fun setupViewModel() {
        menuBoxViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(requireActivity().application))[MenuBoxViewModel::class.java]
    }

    private fun setupAction() {
        binding.mcvMenuboxLogout.setOnClickListener {
            menuBoxViewModel.logout()
            Toast.makeText(requireActivity(), R.string.label_logout_success, Toast.LENGTH_SHORT).show()
        }
    }
}