package com.example.vianaonwheels

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.adapters.ToUseAdapter
import com.example.vianaonwheels.models.ToUse
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
const val EXTRA_TICKET = ""
class ToUseActivity : AppCompatActivity() {
    private val tickets =  ArrayList<ToUse>()
    private lateinit var userEmail: String
    private lateinit var ticketsAdapter: ToUseAdapter //lateinit para iniciar dps a variavel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_use)

        userEmail = intent.getStringExtra(EXTRA_USEREMAIL).toString()

        db= FirebaseFirestore.getInstance()
        val rvTickets = findViewById<RecyclerView>(R.id.rvToUseItem)
        Log.d(TAG, "Email$userEmail")
        db.collection("Tickets")
            .whereEqualTo("email", userEmail)
            .whereEqualTo("used", "n")
            .get()
            .addOnSuccessListener{ documents ->
                if(documents.isEmpty) {
                    Toast.makeText(this@ToUseActivity, "Sem Bilhetes por Usar", Toast.LENGTH_LONG).show()
                }else{
                    for (d in documents){
                        tickets.add(ToUse(d.data["price"].toString(),d.data["dates"].toString(),d.data["origin_hour"].toString(),
                            d.data["company"].toString(),d.data["destiny"].toString(),d.data["origin"].toString(),
                            d.data["qtd"].toString(),d.data["destiny_hour"].toString()))
                    }
                    ticketsAdapter = ToUseAdapter(tickets)
                    ticketsAdapter.setOnItemClickListener(object: ToUseAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //Intent QR Code send ticket data
                            val ticketAPI = ticketsAdapter.getItem(position)
                            val ticketData = """
                                |Price:${ticketAPI.price};
                                |Origin:${ticketAPI.origin};
                                |Destiny:${ticketAPI.destiny};
                                |Company:${ticketAPI.company};
                                |Date:${ticketAPI.dates};
                                |Origin_Hour:${ticketAPI.origin_hour};
                                |Destiny_Hour:${ticketAPI.destiny_hour};
                                |qtd:${ticketAPI.qtd};""".trimMargin()
                            goToQR(ticketData)

                        }
                    })
                    rvTickets.adapter = ticketsAdapter
                    rvTickets.layoutManager = LinearLayoutManager(this)

                }
            }.addOnFailureListener {
                Toast.makeText(this, getString(R.string.warning), Toast.LENGTH_LONG).show()
            }
    }

    fun goToQR(ticket: String){
        val intent = Intent(this, QR_Code::class.java).apply {
            putExtra(EXTRA_TICKET, ticket)
        }
        startActivity(intent)
    }
}