package com.vodeg.airlines_app.presentation.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vodeg.airlines_app.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}