package com.rickyslash.travis.ui.highlightdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rickyslash.travis.R

class HighlightDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highlight_detail)
    }

    companion object {
        const val EXTRA_HIGHLIGHT_ITEM = "extra_highlight"
    }
}