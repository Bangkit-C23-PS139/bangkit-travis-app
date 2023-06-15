package com.rickyslash.travis.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rickyslash.travis.R
import com.rickyslash.travis.databinding.ActivitySignupBinding
import com.rickyslash.travis.helper.isValidEmail
import com.rickyslash.travis.model.SignupModel
import com.rickyslash.travis.ui.settings.preference.TravelPreferenceActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var signupViewModel: SignupViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun showTravelPreferenceActivity(data: SignupModel) {
        val intent = Intent(this@SignupActivity, TravelPreferenceActivity::class.java)
        intent.putExtra(TravelPreferenceActivity.EXTRA_SIGNUP_ITEM, data)
        startActivity(intent)
    }

    private fun setupView() {
        val spinnerAdapter:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spGender.adapter = spinnerAdapter
    }

    private fun setupViewModel() {
        signupViewModel = ViewModelProvider(this)[SignupViewModel::class.java]
    }

    private fun setupAction() {
        binding.appbarTop.setNavigationOnClickListener { this.onBackPressedDispatcher.onBackPressed() }
        binding.btnSignup.setOnClickListener {
            val email = binding.edtxEmail.text.toString()
            val password = binding.edtxPass.text.toString()
            val name = binding.edtxName.text.toString()
            val age = binding.edtxAge.text.toString()
            val gender = binding.spGender.selectedItem.toString()
            when {
                (!binding.edtxLayoutEmail.error.isNullOrEmpty() || !isValidEmail(email)) -> {
                    Toast.makeText(this@SignupActivity, getString(R.string.err_email_invalid), Toast.LENGTH_SHORT).show()
                }
                (!binding.edtxLayoutPass.error.isNullOrEmpty() || password.length < 8) -> {
                    Toast.makeText(this@SignupActivity, getString(R.string.err_password_less_8), Toast.LENGTH_SHORT).show()
                }
                (!binding.edtxLayoutName.error.isNullOrEmpty()) -> {
                    Toast.makeText(this@SignupActivity, getString(R.string.err_name_empty), Toast.LENGTH_SHORT).show()
                }
                (!binding.edtxAge.error.isNullOrEmpty() || age == "") -> {
                    Toast.makeText(this@SignupActivity, getString(R.string.err_age_empty), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    showTravelPreferenceActivity(
                        SignupModel(
                            name = name,
                            password = password,
                            email = email,
                            gender = gender,
                            age = age.toInt())
                    )
                }
            }
        }
    }
}