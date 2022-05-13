package com.example.vianaonwheels

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.vianaonwheels.models.Cliente
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : AppCompatActivity() {

    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtPass: EditText
    lateinit var edtConfirm: EditText
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        db = FirebaseFirestore.getInstance()

        edtName = findViewById(R.id.sign_up_name)
        edtEmail = findViewById(R.id.edt_insert_email_sign)
        edtPass = findViewById(R.id.edt_insert_pass_sign)
        edtConfirm = findViewById(R.id.edt_insert_pass_confirm)

    }

    private fun  checkAllFieldsInput(): Boolean { //Strings

        if (edtName.length() == 0 && edtEmail.length() == 0 && edtPass.length() == 0 && edtConfirm.length() == 0){
            edtName.error = getString(R.string.name_required)
            edtEmail.error = getString(R.string.email_required)
            edtPass.error = getString(R.string.pass_required)
            edtConfirm.error = getString(R.string.pass_required)
        }

        if (edtName.length() == 0) {
            edtName.error = getString(R.string.name_required)
            return false
        }

        if (edtEmail.length() == 0) {
            edtEmail.error = getString(R.string.email_required)
            return false
        }

        if (edtPass.length() == 0) {
            edtPass.error = getString(R.string.pass_required)
            return false
        } else if (edtPass.length() < 8) {
            edtPass.error = getString(R.string.minimum_characters)
            return false
        }

        if(edtConfirm.length() == 0) {
            edtConfirm.error = getString(R.string.pass_required)
            return false
        }else if (edtConfirm.text.equals(edtPass.text)) {
            edtConfirm.error = getString(R.string.dif_password)
            edtPass.error = getString(R.string.dif_password)
            return false
        }
        // after all validation return true.
        return true
    }

    fun register() {
        if(checkAllFieldsInput()){
            val newUser = Cliente(edtName.text.toString(), edtEmail.text.toString(), edtPass.text.toString(), null, null, null, null)
            db.collection("Cliente")
                .add(newUser)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        //Redirect
                        Log.d(TAG, "Sucesso")
                    }else{
                        Log.d(TAG, "Erro")
                    }
                }.addOnFailureListener {
                    Log.w(TAG, "Erro")
                }
        }
    }

    fun goLogin(view: View) {
        //Voltar para login
    }
}