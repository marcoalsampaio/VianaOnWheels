package com.example.vianaonwheels

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.adapters.ToUseAdapter
import com.example.vianaonwheels.models.ToUse
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
const val EXTRA_TICKET = ""
class ToUseActivity : AppCompatActivity() {
    private val tickets =  ArrayList<ToUse>()
    private lateinit var userEmail: String
    private lateinit var ticketsAdapter: ToUseAdapter //lateinit para iniciar dps a variavel

    //TopBar
    private lateinit var nDrawerLayout: DrawerLayout;
    private lateinit var navView: NavigationView;
    private lateinit var tituloPagina: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_use)

        nDrawerLayout = findViewById(R.id.drawerLayout)
        navView= findViewById(R.id.navView)

        tituloPagina= findViewById(R.id.tituloPagina)
        tituloPagina.setText(R.string.ticket)

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
        finish()
    }

    fun aboutUS(view: View) {
        findViewById<AppCompatButton>(R.id.sign_up)
        val intent = Intent(this, AboutUsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out)
    }
    fun deleteAcc(view: View) {
        val dialogBuilder = AlertDialog.Builder(this)

        db= FirebaseFirestore.getInstance()
        db.collection("User").document(userID).delete()
            .addOnSuccessListener { Log.d(TAG, getString(R.string.account_deleted))
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
            .addOnFailureListener { e -> Log.w(TAG, getString(R.string.error_deleting), e)
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
        val intent = Intent(this, MainPage::class.java).apply {
            putExtra(EXTRA_USEREMAIL, userEmail)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out) }
    fun menuIcon(view: View) {
        nDrawerLayout.openDrawer(navView)
    }
}