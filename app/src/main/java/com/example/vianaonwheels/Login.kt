package com.example.vianaonwheels

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.firestore.FirebaseFirestore
const val EXTRA_USERID = ""
const val EXTRA_USEREMAIL = ""

lateinit var db: FirebaseFirestore
class Login : AppCompatActivity() {

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

    fun login(view: View) {
         //Encriptar dps!!! ?? se tiver tempo
        if(checkAllFieldsInput()){
            db.collection("User")
                .whereEqualTo("email", email.text.toString())
                .whereEqualTo("pass", pass.text.toString())
                .get()
                .addOnCompleteListener{ task ->
                    if(task.result.isEmpty) {
                        pass.text.clear()
                        email.text.clear()
                        email.error = getString(R.string.wrong_cred)
                        pass.error = getString(R.string.wrong_cred)
                    }else{
                        for (d in task.result!!){
                            val intent = Intent(this, MainPage::class.java).apply { //Mudar Class!!!!!!!!!
                                putExtra(EXTRA_USERID, d.id)
                                putExtra(EXTRA_USERID, email.text.toString())
                            }
                            pass.text.clear()
                            email.text.clear()
                            startActivity(intent)
                        }
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

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.O)
    fun showPass(view: View) {
        val show = findViewById<ImageView>(R.id.show)
        if(show.tooltipText.toString() == getString(R.string.show)){
            show.background = getDrawable(R.drawable.ic_hide)
            show.tooltipText = getString(R.string.hide)
            pass.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }else{
            show.background = getDrawable(R.drawable.ic_show)
            show.tooltipText = getString(R.string.show)
            pass.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun goForgot(view: View) {
        findViewById<AppCompatButton>(R.id.forgot)
            .setBackgroundColor(getColor(R.color.cinza_transparent_45))
        val intent = Intent(this, ForgotActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.`in`,R.anim.out)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun register(view: View) {
        findViewById<AppCompatButton>(R.id.sign_up)
            .setBackgroundColor(getColor(R.color.cinza_transparent_45))
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.`in`,R.anim.out)
    }
}

