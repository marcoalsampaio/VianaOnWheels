package com.example.vianaonwheels

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.adapter.HistoricAdapter
import com.example.vianaonwheels.adapters.ToUseAdapter
import com.example.vianaonwheels.models.Historic
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class HistoricActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    private var db =  Firebase.firestore
    val filtros = listOf("Date_Hour", "Company", "Origin", "Destination", "Price")
    private lateinit var userEmail: String
    //TopBar
    private lateinit var nDrawerLayout: DrawerLayout;
    private lateinit var navView: NavigationView;
    private lateinit var tituloPagina: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historic)


        //Carrega os filtros
        getFilters()

        userEmail = intent.getStringExtra(EXTRA_USEREMAIL).toString()

        nDrawerLayout = findViewById(R.id.drawerLayout)
        navView= findViewById(R.id.navView)

        tituloPagina= findViewById(R.id.tituloPagina)
        tituloPagina.text = getString(R.string.historic_title)

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

    fun aboutUS(view: View) {
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
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out)
        finish()
    }


    fun backIcon(view: View) {
        val intent = Intent(this, MainPage::class.java).apply {
            putExtra(EXTRA_USEREMAIL, userEmail)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out) }


    fun menuIcon(view: View) {
        nDrawerLayout.openDrawer(navView)
    }


}