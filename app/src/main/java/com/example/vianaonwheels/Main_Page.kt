package com.example.vianaonwheels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Main_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
    }

    fun goHistory(view: View) {
        val intent = Intent(this, HistoricActivity::class.java)
        startActivity(intent)
    }
    fun goCamera(view: View) {
        val intent = Intent(this, QR_scanner::class.java)
        startActivity(intent)
    }
    fun goCalender(view: View) {
        val intent = Intent(this, horarios::class.java)
        startActivity(intent)
    }
    fun goMap(view: View) {
       /* val intent = Intent(this, SignUp::class.java)
        startActivity(intent)*/
    }
}