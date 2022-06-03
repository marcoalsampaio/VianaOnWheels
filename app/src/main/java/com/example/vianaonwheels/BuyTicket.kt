package com.example.vianaonwheels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vianaonwheels.adapters.buyTicketAdapter
import com.example.vianaonwheels.models.ticketToBuy
import kotlinx.android.synthetic.main.activity_buyticket.*

class BuyTicket : AppCompatActivity() {

    private lateinit var ticketsList : ArrayList<ticketToBuy>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyticket)

        ticketsList = ArrayList<ticketToBuy>()

        for (i in 0 until 3){
            ticketsList.add(ticketToBuy("Porto de gaia", "10:00", "Maia", "11:00", 1, "10.00€"))
        }

        ticketsRV.adapter = buyTicketAdapter(ticketsList)
        ticketsRV.layoutManager = LinearLayoutManager(this)



    }

    fun buyTicket(view: View) {

    }
}