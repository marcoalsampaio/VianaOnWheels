package com.example.vianaonwheels

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vianaonwheels.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    fun searchBus(view: View) {
        val spinner = findViewById<Spinner>(R.id.spinnerOrigem)

        val text: String = spinner.getSelectedItem().toString()

        db.collection("BusInfo").whereEqualTo("name", text).get()
            .addOnSuccessListener{ documents ->
                for (document in documents) {
                    var geoPoint= document.getGeoPoint("paragem")
                    var point= LatLng(geoPoint!!.latitude, geoPoint!!.longitude)
                    mMap.addMarker(MarkerOptions().position(point).title("Viana"))
                }
            }
            .addOnFailureListener {exception ->
                Log.w(ContentValues.TAG, "Error getting user data: ", exception)
            }



    }
}