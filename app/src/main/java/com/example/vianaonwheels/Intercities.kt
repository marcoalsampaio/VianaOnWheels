package com.example.vianaonwheels

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class Intercities : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intercities)
    }

    fun search(view: View) {
        startActivity(Intent(this@Intercities, IntercitiesSearch::class.java))
    }
}