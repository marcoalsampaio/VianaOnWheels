package com.example.vianaonwheels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

class HistoricActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historic)

    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.hc_menu, menu);
        return true;
    }
}