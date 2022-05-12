package com.example.vianaonwheels

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vianaonwheels.models.Cliente
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class Login : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore;

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        db= FirebaseFirestore.getInstance();
    }

    fun login(view: View) {
        val email = findViewById<EditText>(R.id.edt_insert_email).text.toString()
        val pass = findViewById<EditText>(R.id.edt_insert_pass).text.toString() //Encriptar dps!!! ?? se tiver tempo
        Log.d(TAG,email)
        if (email.isNotEmpty() && pass.isNotEmpty()){
                db.collection("Cliente")
                    .whereEqualTo("Email", email)
                    .whereEqualTo("Password", pass)
                    .get()
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            for (d in task.result!!){
                                Log.d(TAG,d.id) //Ver Resto!!!!! Passsar ID para proxima atividade
                            }
                        }
                    }.addOnFailureListener {
                        Log.w(TAG, "", it)
                    }
        }else{
            //Mensagem Inserir daods corretos
            Toast.makeText(this, "Insira os dados corretos", Toast.LENGTH_LONG).show()
        }
    }

    //Receber dados
    //conectar a base de dados
    //verificar credenciais
    //apresentar mensagemada
    // Enviar ID?? para outra ativide
}

