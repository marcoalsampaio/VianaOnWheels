package com.example.vianaonwheels

import android.app.AlertDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore

class ForgotActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var email: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        db = FirebaseFirestore.getInstance()
    }

    fun sendEmail(view: View) {

        val dialogBuilder = AlertDialog.Builder(this)

        //Check if a email is inserted
        //If exist send a email with user name and password
        //Always show mensagem in aler saying "A email was sent with the information"
            val collectionUser = db.collection("User")

            collectionUser.whereEqualTo("email", email.text.toString())
                .get().addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        if(!task.result.isEmpty) {
                            email.error = "Invalid email format"
                        }else{

                            dialogBuilder.setPositiveButton(getString(R.string.ok)) { _, _ ->
                                Log.d(ContentValues.TAG, "Enter")
                            }
                            // create dialog box
                            val alert = dialogBuilder.create()
                            // set title for alert dialog box
                            alert.setTitle("An email was sent for the following email: " + occultEmail(email.text.toString()))
                            // show alert dialog
                            alert.show()
                        }
                    }
                }
    }

    fun occultEmail(email: String): String {
        var asterisk = "*****"
        var arr = email.split('@').toList()
        return email.substring(0, 1) + asterisk + arr[1]
    }
}


