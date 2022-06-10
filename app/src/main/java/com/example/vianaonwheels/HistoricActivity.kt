package com.example.vianaonwheels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.adapter.HistoricAdapter
import com.example.vianaonwheels.adapters.ToUseAdapter
import com.example.vianaonwheels.models.Historic
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class HistoricActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    private var db =  Firebase.firestore
    val filtros = listOf("Date_Hour", "Company", "Origin", "Destination", "Price")
    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historic)


        //Carrega os filtros
        getFilters()
        //Apresenta o back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userEmail = intent.getStringExtra(EXTRA_USEREMAIL).toString()

        //Atribui o recycleview a uma variavel
        val recyclerView = findViewById<RecyclerView>(R.id.rl_source)

        //OBTEM E EVNIA OS DADOS PARA O ADAPTAR DO RECYCLER VIEW
        val historic: ArrayList<Historic> = ArrayList()
        db.collection("Tickets")
            .whereEqualTo("email", userEmail)
            .whereEqualTo("used", "s")
            .get()
            .addOnSuccessListener{ documents ->
                if(documents.isEmpty) {
                    Toast.makeText(this, "Sem Histórico", Toast.LENGTH_LONG).show()
                }else{
                    for (d in documents){
                        historic.add(
                            Historic(
                                d.data["origin_hour"].toString(),
                                d.data["destiny_hour"].toString(),
                                d.data["company"].toString(),
                                d.data["dates"].toString(),
                                d.data["destiny"].toString(),
                                d.data["origin"].toString(),
                                d.data["price"].toString(),
                                d.data["email"].toString())
                        )
                    }
                    val adapter = HistoricAdapter(historic)
                    recyclerView.adapter = adapter

                    recyclerView.layoutManager = LinearLayoutManager(this)


                }
            }.addOnFailureListener {
                Toast.makeText(this, getString(R.string.warning), Toast.LENGTH_LONG).show()
            }
    }

                            /* SECÇÃO FILTROS */


    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.hc_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        var relativelayout  = findViewById<RelativeLayout>(R.id.filters)
        return when (item.itemId) {
            R.id.action_filter -> {
                relativelayout.isVisible = !relativelayout.isVisible
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



                                        /* SECÇÃO FILTROS */

    fun getFilters(){
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner!!.onItemSelectedListener = this

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, filtros)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.adapter = aa
    }



    override fun onItemSelected(p0: AdapterView<*>?, arg1: View?, position: Int, id: Long) {

        val recyclerView = findViewById<RecyclerView>(R.id.rl_source)
        val historic: ArrayList<Historic> = ArrayList()
        db.collection("Tickets")
            .whereEqualTo("email", userEmail)
            .whereEqualTo("used", "s")
            .get()
            .addOnSuccessListener{ documents ->
                if(documents.isEmpty) {
                    Toast.makeText(this, "Sem Histórico", Toast.LENGTH_LONG).show()
                }else{
                    for (d in documents){
                        historic.add(
                            Historic(
                                d.data["origin_hour"].toString(),
                                d.data["destiny_hour"].toString(),
                                d.data["company"].toString(),
                                d.data["dates"].toString(),
                                d.data["destiny"].toString(),
                                d.data["origin"].toString(),
                                d.data["price"].toString(),
                                d.data["email"].toString())
                        )
                    }
                    val adapter = HistoricAdapter(historic)
                    recyclerView.adapter = adapter

                    recyclerView.layoutManager = LinearLayoutManager(this)


                }
            }.addOnFailureListener {
                Toast.makeText(this, getString(R.string.warning), Toast.LENGTH_LONG).show()
            }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}