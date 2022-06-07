package com.example.vianaonwheels

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.adapter.IntercitiesAdapter
import com.example.vianaonwheels.models.CartItems
import com.example.vianaonwheels.models.Trips
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.util.*


class IntercitiesSearch : AppCompatActivity() {
    private var db =  Firebase.firestore
    private lateinit var joTickets: String
    private lateinit var adTickets: String
    private lateinit var seTickets: String
    private lateinit var originCity: String
    private lateinit var destinyCity: String
    private lateinit var dateGo: String
    private lateinit var dateBack: String
    private lateinit var fab: FloatingActionButton
    private var checkGoDone = false
    companion object {
        val cart: ArrayList<CartItems> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intercieties_search)

        val intent = intent


        fab = findViewById(R.id.fabVolta)
        val cartCountItemView = findViewById<TextView>(R.id.cart_count)
        cartCountItemView.text = cart.size.toString()
        val cartPriceItemView = findViewById<TextView>(R.id.total_price)
        var total_preco = 0.0
        for(item in cart){
            total_preco += item.preco
        }
        cartPriceItemView.text = total_preco.toString()
        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        joTickets = intent.getStringExtra("n_jo_tickets").toString()
        adTickets = intent.getStringExtra("n_ad_tickets").toString()
        seTickets = intent.getStringExtra("n_se_tickets").toString()
        originCity = intent.getStringExtra("origin_city").toString()
        destinyCity = intent.getStringExtra("destiny_city").toString()
        dateGo = intent.getStringExtra("date_origin").toString()
        dateBack = intent.getStringExtra("date_destiny").toString()
        Toast.makeText(this, dateBack, Toast.LENGTH_SHORT).show()
        val dateSelected = findViewById<TextView>(R.id.tv_date)
        dateSelected.text = dateGo

        getInfo(dateGo, originCity, destinyCity)

    }



    fun openCalendar(view: View) {
        val datePicker = findViewById<DatePicker>(R.id.datePicker2)
        val fab = findViewById<FloatingActionButton>(R.id.fab2)
        datePicker.isVisible = !datePicker.isVisible
        fab.isVisible = !fab.isVisible
        val dateSelected = findViewById<TextView>(R.id.tv_date)

        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            today.set(year,month, day)
            val dayOfWeek = today.getDisplayName( Calendar.DAY_OF_WEEK ,Calendar.LONG, Locale.getDefault());
            val monthOfYear = today.getDisplayName( Calendar.MONTH ,Calendar.LONG, Locale.getDefault());

            dateSelected.text = dayOfWeek + ", " + day + " " + monthOfYear + " " + year
            val newDate = "$day-$month-$year"
            getInfo(newDate, originCity, destinyCity)
            Toast.makeText(this, "$dayOfWeek, $day $monthOfYear $year", Toast.LENGTH_SHORT).show()
        }
    }

    fun getTickets(view: View) {
        val datePicker = findViewById<DatePicker>(R.id.datePicker2)
        val fab = findViewById<FloatingActionButton>(R.id.fab2)
        datePicker.isVisible = !datePicker.isVisible
        fab.isVisible = !fab.isVisible

        //PESQUISAR BILHETES COM O INICIO NA DATA ESTIPULADA E ORIGEM E DESTINO QUE O UTILIZADOR ESCOLHEU
    }

    fun getInfo(dateGo: String?, originCity: String?, destinyCity: String?){
        val cartCount = findViewById<TextView>(R.id.cart_count)
        val priceCart = findViewById<TextView>(R.id.total_price)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val tickets: ArrayList<Trips> = ArrayList()
        if (dateGo != null && !checkGoDone) {
            db.collection("Intercities")
                .whereEqualTo("origin", originCity)
                .whereEqualTo("destiny", destinyCity)
                .whereEqualTo("Date", dateGo)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        tickets.add(Trips(document.data["basePrice"].toString().toFloat(),
                            document.data["beginHour"].toString(),
                            document.data["endHour"].toString(),
                            document.data["company"].toString(),
                            document.data["connections"].toString().toInt(),
                            document.data["destiny"].toString(),
                            document.data["totalKms"].toString().toInt(),
                            document.data["origin"].toString()
                        ))
                    }

                    val adapter = IntercitiesAdapter(tickets, cartCount, priceCart, cart, fab, dateBack)
                    recyclerView.adapter = adapter

                    recyclerView.layoutManager = LinearLayoutManager(this)
                }
                .addOnFailureListener { exeception ->
                    Log.d("DataNotRecieve", "teste$exeception")

                }
        }
        if( dateBack.isNotEmpty() && checkGoDone){
            db.collection("Intercities")
                .whereEqualTo("origin", destinyCity)
                .whereEqualTo("destiny", originCity)
                .whereEqualTo("Date", dateBack)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        tickets.add(Trips(document.data["basePrice"].toString().toFloat(),
                            document.data["beginHour"].toString(),
                            document.data["endHour"].toString(),
                            document.data["company"].toString(),
                            document.data["connections"].toString().toInt(),
                            document.data["destiny"].toString(),
                            document.data["totalKms"].toString().toInt(),
                            document.data["origin"].toString()
                        ))
                    }

                    val adapter = IntercitiesAdapter(tickets, cartCount, priceCart, cart, fab, dateBack)
                    recyclerView.adapter = adapter

                    recyclerView.layoutManager = LinearLayoutManager(this)
                }
                .addOnFailureListener { exeception ->
                    Log.d("DataNotRecieve", "teste$exeception")

                }
        }
    }


    fun chooseTicket(view: View) {
        val cartCount = findViewById<TextView>(R.id.cart_count)
        val priceCart = findViewById<TextView>(R.id.total_price)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val tickets: ArrayList<Trips> = ArrayList()
        if (dateBack != null) {
            db.collection("Intercities")
                .whereEqualTo("origin", destinyCity)
                .whereEqualTo("destiny", originCity)
                .whereEqualTo("Date", dateBack)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        tickets.add(Trips(document.data["basePrice"].toString().toFloat(),
                            document.data["beginHour"].toString(),
                            document.data["endHour"].toString(),
                            document.data["company"].toString(),
                            document.data["connections"].toString().toInt(),
                            document.data["destiny"].toString(),
                            document.data["totalKms"].toString().toInt(),
                            document.data["origin"].toString()
                        ))
                    }

                    val adapter = IntercitiesAdapter(tickets, cartCount, priceCart, cart, fab, dateBack)
                    recyclerView.adapter = adapter

                    recyclerView.layoutManager = LinearLayoutManager(this)
                }
                .addOnFailureListener { exeception ->
                    Log.d("DataNotRecieve", "teste$exeception")

                }
        }
        fab.isVisible = false
        checkGoDone = true
    }
}





