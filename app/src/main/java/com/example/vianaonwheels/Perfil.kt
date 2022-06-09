package com.example.vianaonwheels

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.drawerlayout.widget.DrawerLayout
import com.example.vianaonwheels.models.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class Perfil : AppCompatActivity() {
    private lateinit var userEmail : String;

    private lateinit var user_name: EditText;
    private lateinit var user_birth: EditText;
    private lateinit var user_mail: EditText;
    private lateinit var user_contact: EditText;
    private lateinit var user_id: String;
    private lateinit var userCollection: CollectionReference
    private lateinit var nDrawerLayout: DrawerLayout;
    private lateinit var navView: NavigationView;
    private lateinit var tituloPagina: TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        userEmail = intent.getStringExtra(EXTRA_USEREMAIL).toString() //Email

        nDrawerLayout = findViewById(R.id.drawerLayout)
        navView= findViewById(R.id.navView)

        tituloPagina= findViewById(R.id.tituloPagina)
        tituloPagina.text = "Perfil"

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

    fun aboutUS(view: View) {
        findViewById<AppCompatButton>(R.id.sign_up)
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
        findViewById<AppCompatButton>(R.id.sign_up)
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out)
        finish()
    }
    fun backIcon(view: View) {
        findViewById<AppCompatButton>(R.id.sign_up)
        val intent = Intent(this, MainPage::class.java).apply {
            putExtra(EXTRA_USEREMAIL, userEmail)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out) }
    fun menuIcon(view: View) {
        nDrawerLayout.openDrawer(navView)
    }
}

