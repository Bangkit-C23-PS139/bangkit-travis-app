package com.rickyslash.travis.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rickyslash.travis.R
import com.rickyslash.travis.databinding.ActivityLoginBinding
import com.rickyslash.travis.databinding.FragmentHomeBinding
import com.rickyslash.travis.helper.ViewModelFactory
import com.rickyslash.travis.helper.isValidEmail
import com.rickyslash.travis.ui.highlight.HighlightActivity
import com.rickyslash.travis.ui.highlight.HighlightViewModel
import com.rickyslash.travis.ui.main.MainActivity
import com.rickyslash.travis.ui.signup.SignupActivity
import com.rickyslash.travis.ui.travelplan.TravelPlanActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private var isErrorObserver: Observer<Boolean>? = null
    private var responseMessageObserver: Observer<String?>? = null
    private var isLoadingObserver: Observer<Boolean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(this, ViewModelFactory(application))[LoginViewModel::class.java]
    }

    private fun setupAction() {
        binding.appbarTop.setNavigationOnClickListener { this.onBackPressedDispatcher.onBackPressed() }
        binding.btnLogin.setOnClickListener {
            val email = binding.edtxEmail.text.toString()
            val password = binding.edtxPass.text.toString()
            when {
                (!binding.edtxLayoutEmail.error.isNullOrEmpty() || !isValidEmail(email)) -> {
                    Toast.makeText(this@LoginActivity, getString(R.string.err_email_invalid), Toast.LENGTH_SHORT).show()
                }
                (!binding.edtxLayoutPass.error.isNullOrEmpty() || password.length < 8) -> {
                    Toast.makeText(this@LoginActivity, getString(R.string.err_password_less_8), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    isLoadingObserver = Observer { showLoading(it) }
                    isLoadingObserver?.let { loginViewModel.isLoading.observe(this, it) }
                    loginViewModel.userLogin(email, password)
                    isErrorObserver = Observer { isError ->
                        if (!isError) {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                    }
                    responseMessageObserver = Observer { responseMessage ->
                        if (responseMessage != null && loginViewModel.isError.value == true) {
                            Toast.makeText(this, responseMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                    isErrorObserver?.let { loginViewModel.isError.observe(this, it) }
                    responseMessageObserver?.let { loginViewModel.responseMessage.observe(this, it) }
                }
            }
        }
        binding.btnSignup.setOnClickListener { startActivity(Intent(this, SignupActivity::class.java)) }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loginProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        isErrorObserver?.let(loginViewModel.isError::removeObserver)
        responseMessageObserver?.let(loginViewModel.responseMessage::removeObserver)
        isLoadingObserver?.let(loginViewModel.isLoading::removeObserver)
    }

}