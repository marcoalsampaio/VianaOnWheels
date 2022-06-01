package com.example.vianaonwheels

import android.app.AlertDialog
import android.content.ContentValues
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
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore

lateinit var userID : String;

class MainPage : AppCompatActivity() {
    //TopBar
    private lateinit var nDrawerLayout: DrawerLayout;
    private lateinit var navView: NavigationView;
    private lateinit var tituloPagina: TextView;
    private lateinit var userEmail : String;
    //mainPage Profile
    private lateinit var user_name: TextView;
    private lateinit var user_birth: TextView;
    private lateinit var user_mail: TextView;
    private lateinit var user_contact: TextView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        userID = intent.getStringExtra(EXTRA_USERID).toString()
        userEmail = intent.getStringExtra(EXTRA_USEREMAIL).toString() //Email

        nDrawerLayout = findViewById(R.id.drawerLayout)
        navView= findViewById(R.id.navView)

        tituloPagina= findViewById(R.id.tituloPagina)
        tituloPagina.setText(R.string.homePage)

        //atribuição dos valores
        user_name = findViewById(R.id.TV_mainName)
        user_name.setText()
        user_birth = findViewById(R.id.user_birth)
        user_birth.setText()
        user_mail = findViewById(R.id.user_mail)
        user_mail.setText()
        user_contact = findViewById(R.id.user_contact)
        user_contact.setText()

    }

    fun goHistory(view: View) {
        val intent = Intent(this, HistoricActivity::class.java)
        startActivity(intent)
    }
    fun goCamera(view: View) {
        val intent = Intent(this, QR_scanner::class.java)
        startActivity(intent)
    }
    fun goCalender(view: View) {
        val intent = Intent(this, HorariosActivity::class.java)

    }
    fun goTickets(view: View) {
        val intent = Intent(this, ToUseActivity::class.java).apply {
            putExtra(EXTRA_USEREMAIL, userEmail)
        }
        startActivity(intent)
    }
    fun goMap(view: View) {
       /* val intent = Intent(this, AboutUsActivity::class.java)
        startActivity(intent)*/
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
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out)
    }
    fun menuIcon(view: View) {
        nDrawerLayout.openDrawer(navView)
    }


}