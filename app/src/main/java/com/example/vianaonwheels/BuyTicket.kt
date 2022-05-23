package com.example.vianaonwheels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class BuyTicket : AppCompatActivity() {
    private lateinit var  cardBuy : TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyticket)

        cardBuy=findViewById(R.id.cardBuy)
        cardBuy.text="Partida:" + getString(R.string.tab) + "18:30"+ "-" + "Maia"+ "\nChegada:"+ getString(R.string.tab)  + "18:30"+ "-" + "Maia" + "\nPreço:"+ getString(R.string.tab)  + "2,55€"
    }

    fun buyTicket(view: View) {

    }
}