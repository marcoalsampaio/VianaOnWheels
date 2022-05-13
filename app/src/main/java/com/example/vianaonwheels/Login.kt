package com.example.vianaonwheels

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class Login : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    private lateinit var email: EditText
    private lateinit var pass: EditText

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        db= FirebaseFirestore.getInstance()
        email = findViewById(R.id.edt_insert_email)
        pass = findViewById(R.id.edt_insert_pass)
    }

    fun login() {
         //Encriptar dps!!! ?? se tiver tempo
        if(checkAllFieldsInput()){
            db.collection("Cliente")
                .whereEqualTo("Email", email.text.toString())
                .whereEqualTo("Password", pass.text.toString())
                .get()
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        for (d in task.result!!){
                            Log.d(TAG,d.id) //Ver Resto!!!!! Passsar ID para proxima atividade
                        }
                        email.error = getString(R.string.wrong_cred)
                        pass.error = getString(R.string.wrong_cred)
                    }else{
                        Toast.makeText(this, getString(R.string.warning), Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, getString(R.string.warning), Toast.LENGTH_LONG).show()
                }
        }
    }
    private fun  checkAllFieldsInput(): Boolean { //Strings

        if (email.length() == 0 && pass.length() == 0){
            email.error = getString(R.string.email_required)
            pass.error = getString(R.string.pass_required)
            return false
        }
        if (email.length() == 0){
            email.error = getString(R.string.email_required)
            return false
        }

        if (pass.length() == 0){
            pass.error = getString(R.string.pass_required)
            return false
        }

        // after all validation return true.
        return true
    }


    //Receber dados
    //conectar a base de dados
    //verificar credenciais
    //apresentar mensagemada
    // Enviar ID?? para outra ativide
}

