package com.example.vianaonwheels

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.vianaonwheels.models.User
import com.google.firebase.firestore.CollectionReference

class Perfil : AppCompatActivity() {
    private lateinit var userEmail : String;

    private lateinit var user_name: EditText;
    private lateinit var user_birth: EditText;
    private lateinit var user_mail: EditText;
    private lateinit var user_contact: EditText;
    private lateinit var user_id: String;
    private lateinit var userCollection: CollectionReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        userEmail = intent.getStringExtra(EXTRA_USEREMAIL).toString() //Email

        user_name = findViewById(R.id.edit_name)
        user_birth = findViewById(R.id.edit_birth)
        user_mail = findViewById(R.id.edit_mail)
        user_contact = findViewById(R.id.edit_Phone)
        userCollection = db.collection("User")
        userCollection.whereEqualTo("email", userEmail).get()
            .addOnSuccessListener{ documents ->
                for (document in documents) {
                    user_id = document.id
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
        var email: String?
        var name: String?
        var birth: String?
        var contact: Int?
        userCollection.document(user_id).get().addOnSuccessListener { document ->
            email = userEmail.ifEmpty {
                document["email"].toString()
            }
            name = user_name.text.toString().ifEmpty {
                document["name"].toString()
            }
            birth = user_birth.text.toString().ifEmpty {
                document["birthDate"].toString()
            }
            if(user_contact.text.isNullOrBlank()){
                contact = document["phoneNumber"].toString().toInt()
            }else{
                contact = user_contact.text.toString().toInt()
            }
            var user = User(name!!,email!!, document["pass"].toString(), null, birth!!, contact!!, null)
            userCollection.document(user_id).set(user).addOnSuccessListener{
                Toast.makeText(this, "Sucess", Toast.LENGTH_LONG).show()

                val intent = Intent(this, MainPage::class.java).apply { //Mudar Class!!!!!!!!!
                    putExtra(EXTRA_USERID, user_id)
                    putExtra(EXTRA_USERID, email!!)
                }
                startActivity(intent)
                finish()

            }.addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting user data: ", exception) }
        }.addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting user data: ", exception) }


    }

}

