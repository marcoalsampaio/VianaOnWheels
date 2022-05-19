package com.example.vianaonwheels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    //TopBar
    private lateinit var nDrawerLayout: DrawerLayout;
    private lateinit var navView: NavigationView;



    private lateinit var backIcon: ImageView;
    private lateinit var menuIcon: ImageView;
    private lateinit var tituloPagina: TextView;
    private lateinit var nav_aboutUS: Button;
    private lateinit var nav_deleteAcc: Button;
    private lateinit var nav_logOut: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nDrawerLayout = findViewById(R.id.drawerLayout)
        navView= findViewById(R.id.navView)

        backIcon= findViewById(R.id.backIcon)
        menuIcon= findViewById(R.id.menuIcon)
        tituloPagina= findViewById(R.id.tituloPagina)
       /* nav_aboutUS= findViewById(R.id.nav_aboutUS)
        nav_deleteAcc= findViewById(R.id.nav_deleteAcc)
        nav_logOut= findViewById(R.id.nav_logOut) */

        tituloPagina.setText("Titulo")

        backIcon.setOnClickListener { view ->
            Toast.makeText(this, "Voltar Página Aqui", Toast.LENGTH_LONG).show()
        }

        menuIcon.setOnClickListener { view ->
            nDrawerLayout.openDrawer(navView)
        }

     /*   nav_aboutUS.setOnClickListener { view ->
            Toast.makeText(this, "nav_aboutUS", Toast.LENGTH_LONG).show()
        }


        nav_deleteAcc.setOnClickListener { view ->
            Toast.makeText(this, "nav_deleteAcc", Toast.LENGTH_LONG).show()
        }


        nav_logOut.setOnClickListener { view ->
            Toast.makeText(this, "nav_logOut", Toast.LENGTH_LONG).show()
        }*/



    }
}