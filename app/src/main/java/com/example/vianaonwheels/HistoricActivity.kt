package com.example.vianaonwheels

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.adapter.HistoricAdapter
import com.example.vianaonwheels.models.Historic
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.concurrent.Flow
import kotlin.collections.ArrayList

class HistoricActivity : AppCompatActivity() {
    private var db =  Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historic)

        val recyclerView = findViewById<RecyclerView>(R.id.rl_source)
        val historic: ArrayList<Historic> = ArrayList()
        db.collection("Historic")
            .whereEqualTo("c_email", "marcosampaio@ipvc.pt")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {

                    historic.add(Historic(document.data["Company"].toString(),
                        document.getTimestamp("Date_Hour")?.toDate(), document.data["Destination"].toString(), document.data["Origin"].toString(),
                        document.data["Price"] as Double, document.data["c_email"].toString()))

                }

                val adapter = HistoricAdapter(historic)
                recyclerView.adapter = adapter

                recyclerView.layoutManager = LinearLayoutManager(this)

            }
            .addOnFailureListener { exception ->

            }



        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.



    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.hc_menu, menu);
        return true;
    }



}