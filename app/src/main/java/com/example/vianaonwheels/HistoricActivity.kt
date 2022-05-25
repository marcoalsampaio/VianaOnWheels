package com.example.vianaonwheels

import android.graphics.Color
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
import com.example.vianaonwheels.models.Historic
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class HistoricActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    private var db =  Firebase.firestore
    val filtros = listOf("Date_Hour", "Company", "Origin", "Destination", "Price")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historic)


        //Carrega os filtros
        getFilters()
        //Apresenta o back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Atribui o recycleview a uma variavel
        val recyclerView = findViewById<RecyclerView>(R.id.rl_source)

        //OBTEM E EVNIA OS DADOS PARA O ADAPTAR DO RECYCLER VIEW
        val historic: ArrayList<Historic> = ArrayList()
        db.collection("Historic")
            .whereEqualTo("c_email", "marcosampaio@ipvc.pt")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {

                    historic.add(
                        Historic(
                            document.data["Company"].toString(),
                            document.getTimestamp("Date_Hour")?.toDate(),
                            document.data["Destination"].toString(),
                            document.data["Origin"].toString(),
                            document.data["Price"] as Double,
                            document.data["c_email"].toString()
                        )
                    )

                }

                val adapter = HistoricAdapter(historic)
                recyclerView.adapter = adapter

                recyclerView.layoutManager = LinearLayoutManager(this)

            }
            .addOnFailureListener { exception ->

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
        db.collection("Historic")
            .whereEqualTo("c_email", "marcosampaio@ipvc.pt")
            .orderBy(filtros[position])
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {

                    historic.add(
                        Historic(
                            document.data["Company"].toString(),
                            document.getTimestamp("Date_Hour")?.toDate(),
                            document.data["Destination"].toString(),
                            document.data["Origin"].toString(),
                            document.data["Price"] as Double,
                            document.data["c_email"].toString()
                        )
                    )

                }
                for (element in historic) {
                    println(element)
                }
                val adapter = HistoricAdapter(historic)
                recyclerView.adapter = adapter

                recyclerView.layoutManager = LinearLayoutManager(this)

            }
            .addOnFailureListener { exception ->

            }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}