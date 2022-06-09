package com.example.vianaonwheels

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.adapter.IntercitiesAdapter
import com.example.vianaonwheels.models.CartItems
import com.example.vianaonwheels.models.Trips
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

val cart: ArrayList<CartItems> = ArrayList()
class IntercitiesSearch : AppCompatActivity() {
    private var db =  Firebase.firestore
    private lateinit var joTickets: String
    private lateinit var adTickets: String
    private lateinit var seTickets: String
    private lateinit var originCity: String
    private lateinit var destinyCity: String
    private lateinit var dateGo: String
    private lateinit var dateBack: String
    private lateinit var fab: Button
    private var checkGoDone = false


    private lateinit var userEmail : String
    //TopBar
    private lateinit var nDrawerLayout: DrawerLayout;
    private lateinit var navView: NavigationView;
    private lateinit var tituloPagina: TextView;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intercieties_search)

        userEmail = intent.getStringExtra(EXTRA_USEREMAIL).toString()

        nDrawerLayout = findViewById(R.id.drawerLayout)
        navView= findViewById(R.id.navView)

        tituloPagina= findViewById(R.id.tituloPagina)
        tituloPagina.text = "Teste"


        val intent = intent
        fab = findViewById(R.id.fabVolta)
        val cartCountItemView = findViewById<TextView>(R.id.cart_count)
        cartCountItemView.text = getString(R.string.icities_search_cart)  + cart.size.toString()
        val cartPriceItemView = findViewById<TextView>(R.id.total_price)
        var total_preco = 0.0
        for(item in cart){
            total_preco += item.preco
        }
        cartPriceItemView.text = getString(R.string.icities_search_price) + total_preco.toString() + "€"
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
        if (dateBack.isEmpty() && cart.size == 0){
            fab.isVisible = false
        }


        getInfo()

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
            getInfo()
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

    fun getInfo(){
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
                        tickets.add(Trips(
                            document.data["Date"].toString(),
                            document.data["basePrice"].toString().toFloat(),
                            document.data["beginHour"].toString(),
                            document.data["endHour"].toString(),
                            document.data["company"].toString(),
                            document.data["connections"].toString().toInt(),
                            document.data["destiny"].toString(),
                            document.data["totalKms"].toString().toInt(),
                            document.data["origin"].toString()
                        ))
                    }

                    val adapter = IntercitiesAdapter(tickets, cartCount, priceCart, cart)
                    recyclerView.adapter = adapter

                    recyclerView.layoutManager = LinearLayoutManager(this)
                }
                .addOnFailureListener { exeception ->
                    Log.d("DataNotRecieve", "teste$exeception")

                }
        }else if(dateBack != null && checkGoDone){
            db.collection("Intercities")
                .whereEqualTo("origin", destinyCity)
                .whereEqualTo("destiny", originCity)
                .whereEqualTo("Date", dateBack)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        tickets.add(Trips(
                            document.data["Date"].toString(),
                            document.data["basePrice"].toString().toFloat(),
                            document.data["beginHour"].toString(),
                            document.data["endHour"].toString(),
                            document.data["company"].toString(),
                            document.data["connections"].toString().toInt(),
                            document.data["destiny"].toString(),
                            document.data["totalKms"].toString().toInt(),
                            document.data["origin"].toString()
                        ))
                    }

                    val adapter = IntercitiesAdapter(tickets, cartCount, priceCart, cart)
                    recyclerView.adapter = adapter

                    recyclerView.layoutManager = LinearLayoutManager(this)
                }
                .addOnFailureListener { exeception ->
                    Log.d("DataNotRecieve", "teste$exeception")

                }
        }

    }


    fun chooseTicket(view: View) {
        checkGoDone = !checkGoDone
        val date = findViewById<TextView>(R.id.tv_date)


        if(!checkGoDone){
            fab.text = getString(R.string.icities_search_back)
            getInfo()
            /*val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            cal.time = sdf.parse(dateGo) // all done
            val dayOfWeek = cal.getDisplayName( Calendar.DAY_OF_WEEK ,Calendar.LONG, Locale.getDefault());
            val monthOfYear = cal.getDisplayName( Calendar.MONTH ,Calendar.LONG, Locale.getDefault());*/
            date.text = dateGo

        }
        if(checkGoDone){
            fab.text = getString(R.string.icities_search_go)
            getInfo()
            /*val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
            cal.time = sdf.parse(dateBack) // all done
            val dayOfWeek = cal.getDisplayName( Calendar.DAY_OF_WEEK ,Calendar.LONG, Locale.getDefault());
            val monthOfYear = cal.getDisplayName( Calendar.MONTH ,Calendar.LONG, Locale.getDefault());*/
            date.text = dateBack
        }
        /*val cartCount = findViewById<TextView>(R.id.cart_count)
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

                    val adapter = IntercitiesAdapter(tickets, cartCount, priceCart, cart)
                    recyclerView.adapter = adapter

                    recyclerView.layoutManager = LinearLayoutManager(this)
                }
                .addOnFailureListener { exeception ->
                    Log.d("DataNotRecieve", "teste$exeception")

                }
        }
        fab.isVisible = false
        checkGoDone = true*/
    }

    fun openCart(view: View) {
        for(item in cart){
            Log.d("Teste", item.origem)
        }
        val intent = Intent(this, BuyTicket::class.java)
        intent.putExtra("intercities", "true")
        startActivity(intent)

    }



    fun aboutUS(view: View) {
        findViewById<AppCompatButton>(R.id.sign_up)
        val intent = Intent(this, AboutUsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out)
    }
    fun deleteAcc(view: View) {
        val dialogBuilder = AlertDialog.Builder(this)

        com.example.vianaonwheels.db = FirebaseFirestore.getInstance()
        com.example.vianaonwheels.db.collection("User").document(userID).delete()
            .addOnSuccessListener { Log.d(ContentValues.TAG, getString(R.string.account_deleted))
                //Mensagem após Eliminar Conta
                dialogBuilder.setPositiveButton(/*getString(R.string.ok)*/"OK") { _, _ ->
                    //Redirect to Login
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.out_in,R.anim.in_out)
                }
                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle(getString(R.string.account_deleted))
                // show alert dialog
                alert.show()
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, getString(R.string.error_deleting), e)
                Toast.makeText(this,  getString(R.string.error_deleting), Toast.LENGTH_LONG).show()}
    }
    fun logout(view: View) {
        findViewById<AppCompatButton>(R.id.sign_up)
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out)
        finish()
    }

    fun backIcon(view: View) {
        findViewById<AppCompatButton>(R.id.sign_up)
        Intent(this, Intercities::class.java).apply {
            putExtra(EXTRA_USEREMAIL, userEmail)
        }

        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out) }


    fun menuIcon(view: View) {
        nDrawerLayout.openDrawer(navView)
    }
}





