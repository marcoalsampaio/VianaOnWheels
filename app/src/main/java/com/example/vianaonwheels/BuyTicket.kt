package com.example.vianaonwheels

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vianaonwheels.adapters.buyTicketAdapter
import com.example.vianaonwheels.models.InsertTicket
import com.example.vianaonwheels.models.ticketToBuy
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore

class BuyTicket : AppCompatActivity() {

    private lateinit var ticketsList : ArrayList<ticketToBuy>
    private lateinit var totalTV : TextView
    private lateinit var ticketsRV : RecyclerView
    private lateinit var userEmail : String
    private lateinit var intercitiePage : String
    //TopBar
    private lateinit var nDrawerLayout: DrawerLayout;
    private lateinit var navView: NavigationView;
    private lateinit var tituloPagina: TextView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyticket)

        nDrawerLayout = findViewById(R.id.drawerLayout)
        navView= findViewById(R.id.navView)

        tituloPagina= findViewById(R.id.tituloPagina)
        tituloPagina.setText(R.string.carrinho)


        userEmail = intent.getStringExtra(EXTRA_USEREMAIL).toString()
        intercitiePage = intent.getStringExtra("intercities").toString()
        db = FirebaseFirestore.getInstance()

        totalTV = findViewById(R.id.Total)
        ticketsRV = findViewById(R.id.ticketsRV)

        ticketsList = ArrayList<ticketToBuy>()

        for (item in cart){
            ticketsList.add(ticketToBuy(item.ida, item.company,item.origem, item.beginHour, item.destino, item.endHour, 1, item.preco.toString().toDouble(), item.preco.toString().toDouble()))
        }

        ticketsRV.adapter = buyTicketAdapter(ticketsList, totalTV)
        ticketsRV.layoutManager = LinearLayoutManager(this)

    }

    fun buyTicket(view: View) {
        val collectionTickets = db.collection("Tickets")
        if(ticketsList.isEmpty()){
            Toast.makeText(this,  getString(R.string.semBilhetes), Toast.LENGTH_LONG).show()

        }else{
            for(ticket in ticketsList){
                val newTicket = InsertTicket(ticket.precoUnitario.toString(), ticket.data, ticket.partidaHora, ticket.company, ticket.destino, ticket.partida, ticket.quantidade.toString(), ticket.destinoHora, userEmail, "n")
                collectionTickets.add(newTicket)
            }
            ticketsList.clear()
            cart.clear()
            Toast.makeText(this,  getString(R.string.compradosSucesso), Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainPage::class.java).apply {
                putExtra(EXTRA_USEREMAIL, userEmail)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.out_in,R.anim.in_out)

        }
    }
    fun aboutUS(view: View) {
        val intent = Intent(this, AboutUsActivity::class.java).apply {
            putExtra(EXTRA_USEREMAIL, userEmail)
        }
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
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out)
        finish()
    }
    fun backIcon(view: View) {
        val intent : Intent = if (intercitiePage != "true") {
            Intent(this, MainPage::class.java).apply {
                putExtra(EXTRA_USEREMAIL, userEmail)
            }
        }else{
            Intent(this, Intercities::class.java).apply {
                putExtra(EXTRA_USEREMAIL, userEmail)
            }
        }
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out) }


    fun menuIcon(view: View) {
        nDrawerLayout.openDrawer(navView)
    }
}