package com.example.vianaonwheels

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

class Perfil : AppCompatActivity() {
    private lateinit var userEmail : String;

    private lateinit var user_name: EditText;
    private lateinit var user_birth: EditText;
    private lateinit var user_mail: EditText;
    private lateinit var user_contact: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        userEmail = intent.getStringExtra(EXTRA_USEREMAIL).toString() //Email

        user_name = findViewById(R.id.edit_name)
        user_birth = findViewById(R.id.edit_birth)
        user_mail = findViewById(R.id.edit_mail)
        user_contact = findViewById(R.id.edit_Phone)

        db.collection("User").whereEqualTo("email", userEmail).get()
            .addOnSuccessListener{ documents ->
                for (document in documents) {
                    user_name.hint=document.data["name"].toString()
                    user_birth.hint = document.data["birthDate"].toString()
                    user_mail.hint = document.data["email"].toString()
                    user_contact.hint = document.data["phoneNumber"].toString()

                }
            }
            .addOnFailureListener {exception ->
                Log.w(ContentValues.TAG, "Error getting user data: ", exception)
            }
    }

    fun saveUser(view: View) {

    }

}