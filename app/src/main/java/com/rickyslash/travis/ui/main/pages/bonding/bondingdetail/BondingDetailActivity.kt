package com.rickyslash.travis.ui.main.pages.bonding.bondingdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rickyslash.travis.R

class BondingDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bonding_detail)
    }

    companion object {
        const val EXTRA_BONDING_ITEM = "extra_bonding"
    }
}