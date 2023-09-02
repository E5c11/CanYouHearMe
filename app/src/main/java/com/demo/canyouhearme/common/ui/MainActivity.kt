package com.demo.canyouhearme.common.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.canyouhearme.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}