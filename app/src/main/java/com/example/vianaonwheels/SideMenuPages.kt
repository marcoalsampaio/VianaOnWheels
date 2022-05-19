package com.example.vianaonwheels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class SideMenuPages : AppCompatActivity() {
    //TopBar
    private lateinit var nDrawerLayout: DrawerLayout;
    private lateinit var navView: NavigationView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nDrawerLayout = findViewById(R.id.drawerLayout)
        navView= findViewById(R.id.navView)

    }

    fun aboutUS(view: View) {
        Toast.makeText(this, "nav_aboutUS", Toast.LENGTH_SHORT).show()
    }
    fun deleteAcc(view: View) {
        Toast.makeText(this, "nav_deleteAcc", Toast.LENGTH_SHORT).show()
    }
    fun logout(view: View) {
        Toast.makeText(this, "nav_logOut", Toast.LENGTH_SHORT).show()
    }
    fun backIcon(view: View) {
        Toast.makeText(this, "Voltar Página Aqui", Toast.LENGTH_SHORT).show()}
    fun menuIcon(view: View) {
        nDrawerLayout.openDrawer(navView)
    }
}