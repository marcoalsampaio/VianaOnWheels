package com.example.vianaonwheels

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.vianaonwheels.api.EndPoints
import com.example.vianaonwheels.api.OutputPost
import com.example.vianaonwheels.api.ServiceBuilder
import com.google.firebase.firestore.FirebaseFirestore
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var email: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        db = FirebaseFirestore.getInstance()
        email = findViewById(R.id.edt_insert_email_request)
    }

    fun sendEmail(view: View) {
        val dialogBuilder = AlertDialog.Builder(this)
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        //Check if a email is inserted
        //If exist send a email with user name and password
        //Always show mensagem in aler saying "A email was sent with the information"
        if(checkEmailValidation()){
            val collectionUser = db.collection("User")
            collectionUser.whereEqualTo("email", email.text.toString())
                .get().addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        if(task.result.isEmpty) {
                            email.error = "Invalid email format"
                        }else{
                            for (result in task.result!!){ //Get dos dados do firebase
                                val jwt = Jwts.builder().claim("email", result["email"]) //Criação de um token com user e password
                                    .claim("pass", result["pass"])
                                    .signWith(SignatureAlgorithm.HS256, "secret".toByteArray()) //encriptação do mesmo
                                    .compact()
                                val call = request.sendEmail(jwt) //Chamar API, post com token

                                call.enqueue(object : Callback<OutputPost>{
                                    override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {
                                        if (response.isSuccessful){
                                            val c: OutputPost = response.body()!!
                                            Toast.makeText(this@ForgotActivity, c.toString(), Toast.LENGTH_SHORT).show()
                                            Log.d(TAG, c.toString())
                                        }
                                    }
                                    override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                                        Toast.makeText(this@ForgotActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                                        Log.d(TAG, "${t.message}")
                                    }
                                })
                            }
                        }
                        //Mensagem de envio de email
                         dialogBuilder.setPositiveButton(/*getString(R.string.ok)*/"OK") { _, _ ->
                             Log.d(TAG, "Enter")
                             //Redirect to Login
                             val intent = Intent(this, Login::class.java)
                             startActivity(intent)
                             overridePendingTransition(R.anim.out_in,R.anim.in_out)
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

    private fun occultEmail(email: String): String {
        val asterisk = "*****"
        val arr = email.split('@').toList()
        return email.substring(0, 1) + asterisk +"@"+ arr[1]
    }
    private fun checkEmailValidation(): Boolean{
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches() || email.length() == 0) {
            email.error = /*getString(R.string.exist_email)*/ "Email Invalido"
            Log.d(TAG, "ERRO")
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun backLogin(view: View) {
        findViewById<AppCompatButton>(R.id.back_login)
            .setBackgroundColor(getColor(R.color.cinza_transparent_45))
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out)
    }

}


