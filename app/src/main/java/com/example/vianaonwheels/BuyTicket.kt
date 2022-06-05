package com.example.vianaonwheels

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vianaonwheels.adapters.buyTicketAdapter
import com.example.vianaonwheels.models.ticketToBuy
import kotlinx.android.synthetic.main.activity_buyticket.*

class BuyTicket : AppCompatActivity() {

    private lateinit var ticketsList : ArrayList<ticketToBuy>
    private lateinit var totalTV : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyticket)
        totalTV = findViewById(R.id.Total)

        ticketsList = ArrayList<ticketToBuy>()

        for (i in 0 until 3){
            ticketsList.add(ticketToBuy("Porto de gaia", "10:00", "Maia", "11:00", 1, 10.22, 10.22))
        }

        ticketsRV.adapter = buyTicketAdapter(ticketsList, totalTV)
        ticketsRV.layoutManager = LinearLayoutManager(this)

    }

     fun setTotalPrice(){
        var precoTotal = 0.00;
         totalTV = findViewById(R.id.Total)
        for (i in ticketsList){
            precoTotal += i.precoTotalLinha
        }
        totalTV.text= precoTotal.toString()
    }
    fun buyTicket(view: View) {

    }
}