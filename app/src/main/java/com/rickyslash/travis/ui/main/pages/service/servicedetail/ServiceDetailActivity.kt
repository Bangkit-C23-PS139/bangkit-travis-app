package com.rickyslash.travis.ui.main.pages.service.servicedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rickyslash.travis.R

class ServiceDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)
    }

    companion object {
        const val EXTRA_SERVICE_ITEM = "extra_service"
    }
}