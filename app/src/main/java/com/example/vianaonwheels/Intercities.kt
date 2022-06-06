package com.example.vianaonwheels

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class Intercities : AppCompatActivity() {
    private var db =  Firebase.firestore
    private var cities : ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intercities)
        val adMax = findViewById<Button>(R.id.adMax);
        val joMax = findViewById<Button>(R.id.joMax);
        val seMax = findViewById<Button>(R.id.seMax);
        val adMin = findViewById<Button>(R.id.adMinus);
        val joMin = findViewById<Button>(R.id.joMinus);
        val seMin = findViewById<Button>(R.id.seMinus);

        cities = getCities()

        searchCities()

        adMax.setOnClickListener(View.OnClickListener {
            val et = findViewById<TextView>(R.id.ad_et)
            var valor = et.text.toString().toInt()
            valor += 1
            et.text = valor.toString()
        })
        joMax.setOnClickListener(View.OnClickListener {
            val et = findViewById<TextView>(R.id.jo_et)
            var valor = et.text.toString().toInt()
            valor += 1
            et.text = valor.toString()
        })
        seMax.setOnClickListener(View.OnClickListener {
            val et = findViewById<TextView>(R.id.se_et)
            var valor = et.text.toString().toInt()
            valor += 1
            et.text = valor.toString()
        })

        adMin.setOnClickListener(View.OnClickListener {
            val et = findViewById<TextView>(R.id.ad_et)
            var valor = et.text.toString().toInt()
            if (valor > 0){
                valor -= 1
                et.text = valor.toString()
            }
        })
        joMin.setOnClickListener(View.OnClickListener {
            val et = findViewById<TextView>(R.id.jo_et)
            var valor = et.text.toString().toInt()
            if (valor > 0){
                valor -= 1
                et.text = valor.toString()
            }

        })
        seMin.setOnClickListener(View.OnClickListener {
            val et = findViewById<TextView>(R.id.se_et)
            var valor = et.text.toString().toInt()
            if (valor > 0){
                valor -= 1
                et.text = valor.toString()
            }
        })

    }

    fun search(view: View) {
        val et_jo = findViewById<TextView>(R.id.jo_et)
        val et_ad = findViewById<TextView>(R.id.ad_et)
        val et_se = findViewById<TextView>(R.id.se_et)
        val go = findViewById<View>(R.id.edt_city_source) as AutoCompleteTextView
        val back = findViewById<View>(R.id.edt_city_destination) as AutoCompleteTextView
        val tv1_go = findViewById<TextView>(R.id.tv1_go)
        val tv2_back = findViewById<TextView>(R.id.tv1_destination)
        if(et_jo.text.toString().toInt() > 0 || et_ad.text.toString().toInt() > 0 || et_se.text.toString().toInt() > 0) {
            if(go.text.isNotEmpty() && back.text.isNotEmpty()){
                if(tv1_go.text.isNotEmpty()){
                    val sharedPreference =  getSharedPreferences("TICKETS_COUNT", Context.MODE_PRIVATE)
                    var editor = sharedPreference.edit()
                    editor.clear()
                    editor.putInt("jo_tickets", et_jo.text.toString().toInt())
                    editor.putInt("ad_tickets", et_jo.text.toString().toInt())
                    editor.putInt("se_tickets", et_jo.text.toString().toInt())
                    editor.commit()
                    val intent = Intent(applicationContext, IntercitiesSearch::class.java)
                    intent.putExtra("n_jo_tickets", et_jo.text.toString().toInt())
                    intent.putExtra("n_ad_tickets", et_ad.text.toString().toInt())
                    intent.putExtra("n_se_tickets", et_se.text.toString().toInt())
                    intent.putExtra("origin_city", go.text.toString())
                    intent.putExtra("destiny_city", back.text.toString())
                    intent.putExtra("date_origin", tv1_go.text.toString())
                    intent.putExtra("date_destiny", tv2_back.text.toString())
                    startActivity(intent);

                }else{
                    Toast.makeText(this, R.string.inter_tickets, Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, R.string.inter_tickets, Toast.LENGTH_SHORT).show()
            }


        }else{
            Toast.makeText(this, R.string.inter_tickets, Toast.LENGTH_SHORT).show()
        }
    }


    fun choosePeople(view: View) {
        val ticketChoice = findViewById<RelativeLayout>(R.id.ticket_choice)
        val ticketsButton = findViewById<Button>(R.id.bt_persons)
        val et_jo = findViewById<TextView>(R.id.jo_et)
        val et_ad = findViewById<TextView>(R.id.ad_et)
        val et_se = findViewById<TextView>(R.id.se_et)

        if(et_jo.text.toString().toInt() > 0 || et_ad.text.toString().toInt() > 0 || et_se.text.toString().toInt() > 0){
            ticketsButton.text = ""
            if (et_jo.text.toString().toInt() > 0){
                ticketsButton.text = ticketsButton.text.toString() + et_jo.text.toString() + " " + getString(R.string.inter_jovem)
            }
            if (et_ad.text.toString().toInt() > 0){
                ticketsButton.text = ticketsButton.text.toString() + et_ad.text.toString() + " " + getString(R.string.inter_adultos)
            }
            if (et_se.text.toString().toInt() > 0){
                ticketsButton.text = ticketsButton.text.toString() + et_se.text.toString() + " " + getString(R.string.inter_senior)
            }
        }else{
                ticketsButton.text =  getString(R.string.inter_tickets)
        }
        ticketChoice.isVisible = !ticketChoice.isVisible
    }

    fun selectGoHour(view: View) {
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        datePicker.isVisible = !datePicker.isVisible
        fab.isVisible = !fab.isVisible
        val dateSelected = findViewById<TextView>(R.id.tv1_go)

        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            today.set(year,month, day)
            val dayOfWeek = today.getDisplayName( Calendar.DAY_OF_WEEK ,Calendar.LONG, Locale.getDefault());
            val monthOfYear = today.getDisplayName( Calendar.MONTH ,Calendar.LONG, Locale.getDefault());

            dateSelected.text = "$day-$month-$year"
            Toast.makeText(this, "$day-$month-$year", Toast.LENGTH_SHORT).show()
        }
    }

    fun getTickets(view: View) {
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        datePicker.isVisible = !datePicker.isVisible
        fab.isVisible = !fab.isVisible

        //PESQUISAR BILHETES COM O INICIO NA DATA ESTIPULADA E ORIGEM E DESTINO QUE O UTILIZADOR ESCOLHEU
    }

    fun selectBackHour(view: View) {
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        datePicker.isVisible = !datePicker.isVisible
        fab.isVisible = !fab.isVisible
        val dateSelected = findViewById<TextView>(R.id.tv1_destination)

        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            today.set(year,month, day)
            val dayOfWeek = today.getDisplayName( Calendar.DAY_OF_WEEK ,Calendar.LONG, Locale.getDefault());
            val monthOfYear = today.getDisplayName( Calendar.MONTH ,Calendar.LONG, Locale.getDefault());

            dateSelected.text = "$day-$month-$year"
            Toast.makeText(this, "$day-$month-$year", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCities(): ArrayList<String> {
            val cities: ArrayList<String> = ArrayList()
            db.collection("Cities")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        cities.add(document.data["name"].toString())
                    }
                }
                .addOnFailureListener {

                }
            return cities
    }

    fun searchCities(){
        // Get a reference to the AutoCompleteTextView in the layout
        // Get a reference to the AutoCompleteTextView in the layout
        val textView = findViewById<View>(R.id.edt_city_source) as AutoCompleteTextView
        val textView2 = findViewById<View>(R.id.edt_city_destination) as AutoCompleteTextView
        // Get the string array
        // Get the string array
                val countries = getCities()
        // Create the adapter and set it to the AutoCompleteTextView
        // Create the adapter and set it to the AutoCompleteTextView

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countries)
        textView.setAdapter(adapter)
        textView2.setAdapter(adapter)
    }

}