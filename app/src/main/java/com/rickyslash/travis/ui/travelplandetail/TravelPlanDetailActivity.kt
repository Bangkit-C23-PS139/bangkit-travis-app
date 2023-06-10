package com.rickyslash.travis.ui.travelplandetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rickyslash.travis.R

class TravelPlanDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_plan_detail)
    }

    companion object {
        const val EXTRA_TRAVEL_PLAN_ITEM = "extra_travel_plan"
    }
}