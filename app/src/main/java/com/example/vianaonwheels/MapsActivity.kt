package com.example.vianaonwheels

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.vianaonwheels.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private  var valor1: Double=0.0
    private  var valor2: Double=0.0

    private lateinit var userEmail : String
    //TopBar
    private lateinit var nDrawerLayout: DrawerLayout;
    private lateinit var navView: NavigationView;
    private lateinit var tituloPagina: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        nDrawerLayout = findViewById(R.id.drawerLayout)
        navView= findViewById(R.id.navView)

        tituloPagina= findViewById(R.id.tituloPagina)
        tituloPagina.setText(R.string.title_activity_maps)
        userEmail = intent.getStringExtra(EXTRA_USEREMAIL).toString()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var arraylist=ArrayList<String>()
        arraylist.add("Paragens")

        //get user data
        db.collection("BusInfo").get()
            .addOnSuccessListener{ documents ->
                for (document in documents) {
                    arraylist.add(document.data["name"].toString())
                }
            }
            .addOnFailureListener {exception ->
                Log.w(ContentValues.TAG, "Error getting user data: ", exception)
            }


        // access the spinner
        val spinner = findViewById<Spinner>(R.id.spinnerOrigem)
        val spinnerDestino = findViewById<Spinner>(R.id.spinnerDestino)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arraylist)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(this@MapsActivity, "origem", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        if (spinnerDestino != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arraylist)
            spinnerDestino.adapter = adapter

            spinnerDestino.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(this@MapsActivity, "destino", Toast.LENGTH_SHORT).show()

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val viana = LatLng(41.69472614654766, -8.832658596566215)
        mMap.addMarker(MarkerOptions().position(viana).title("Viana"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(viana))
        mMap.setMinZoomPreference(10.0f)
        mMap.setMaxZoomPreference(16.0f)
    }

    fun searchBus(view: View) {
        mMap.clear()
        val spinner = findViewById<Spinner>(R.id.spinnerOrigem)
        val spinner2 = findViewById<Spinner>(R.id.spinnerDestino)
        val texOrigem = findViewById<TextView>(R.id.origemTX)
        val texDestino = findViewById<TextView>(R.id.destinoTX)
        val texValor = findViewById<TextView>(R.id.valorTX)

        val text: String = spinner.getSelectedItem().toString()
        val text1: String = spinner2.getSelectedItem().toString()

        
        db.collection("BusInfo").whereEqualTo("name", text).get()
            .addOnSuccessListener{ documents ->
                for (document in documents) {
                    var geoPoint= document.getGeoPoint("paragem")
                    var point= LatLng(geoPoint!!.latitude, geoPoint!!.longitude)
                    mMap.addMarker(MarkerOptions().position(point).title("origem"))
                    texOrigem.text=document.data["name"].toString()
                    valor1= document.getDouble("valor")!!
                }
            }
            .addOnFailureListener {exception ->
                Log.w(ContentValues.TAG, "Error getting user data: ", exception)
            }
        db.collection("BusInfo").whereEqualTo("name", text1).get()
            .addOnSuccessListener{ documents ->
                for (document in documents) {
                    var geoPoint1= document.getGeoPoint("paragem")
                    var point1= LatLng(geoPoint1!!.latitude, geoPoint1!!.longitude)
                    mMap.addMarker(MarkerOptions().position(point1).title("destino"))
                    texDestino.text=document.data["name"].toString()
                    valor2= document.getDouble("valor")!!
                }
            }
            .addOnFailureListener {exception ->
                Log.w(ContentValues.TAG, "Error getting user data: ", exception)
            }

        var var3=0.0
        if(valor1 < valor2){
            var3 = valor2.minus(valor1)
        }else if(valor1 > valor2){
            var3 = valor1.minus(valor2)
        }else{
            var3=0.50
        }
        texValor.text=var3.toString()
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
