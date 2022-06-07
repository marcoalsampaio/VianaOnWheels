package com.example.vianaonwheels

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.adapters.buyTicketAdapter
import com.example.vianaonwheels.models.InsertTicket
import com.example.vianaonwheels.models.ticketToBuy
import com.google.firebase.firestore.FirebaseFirestore

class BuyTicket : AppCompatActivity() {

    private lateinit var ticketsList : ArrayList<ticketToBuy>
    private lateinit var totalTV : TextView
    private lateinit var ticketsRV : RecyclerView
    private lateinit var userEmail : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyticket)


        userEmail = intent.getStringExtra(EXTRA_USEREMAIL).toString()

        db = FirebaseFirestore.getInstance()

        totalTV = findViewById(R.id.Total)
        ticketsRV = findViewById(R.id.ticketsRV)

        ticketsList = ArrayList<ticketToBuy>()

        for (i in 0 until 3){
            ticketsList.add(ticketToBuy("22/10/2022", "Av Minho","Porto de gaia", "10:00", "Maia", "11:00", 1, 10.22, 10.22))
        }

        ticketsRV.adapter = buyTicketAdapter(ticketsList, totalTV)
        ticketsRV.layoutManager = LinearLayoutManager(this)

    }

    fun buyTicket(view: View) {
        val collectionTickets = db.collection("Tickets")

        for(ticket in ticketsList){
            val newTicket = InsertTicket(ticket.precoUnitario.toString(), ticket.data, ticket.partidaHora, ticket.company, ticket.destino, ticket.partida, ticket.quantidade.toString(), ticket.destinoHora, userEmail, "n")
            collectionTickets.add(newTicket)
        }



    }
}