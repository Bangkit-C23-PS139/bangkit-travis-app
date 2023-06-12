package com.rickyslash.travis.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rickyslash.travis.R
import com.rickyslash.travis.databinding.ActivityLoginBinding
import com.rickyslash.travis.databinding.FragmentHomeBinding
import com.rickyslash.travis.ui.highlight.HighlightActivity
import com.rickyslash.travis.ui.signup.SignupActivity
import com.rickyslash.travis.ui.travelplan.TravelPlanActivity

class LoginActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.btnSignup.setOnClickListener { startActivity(Intent(this, SignupActivity::class.java)) }
    }

}