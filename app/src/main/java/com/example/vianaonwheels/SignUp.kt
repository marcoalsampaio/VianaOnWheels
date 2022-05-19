package com.example.vianaonwheels

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.vianaonwheels.models.User
import com.google.firebase.firestore.FirebaseFirestore


class SignUp : AppCompatActivity() {

    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtPass: EditText
    lateinit var edtConfirm: EditText

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

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString()).matches()) {
            edtEmail.error = getString(R.string.exist_email)
            return false
        }
        // after all validation return true.
        return true
    }

    fun register(view: View) {

        val dialogBuilder = AlertDialog.Builder(this)

        if(checkAllFieldsInput()){
            val collectionUser = db.collection("User")

            collectionUser.whereEqualTo("email", edtEmail.text.toString())
                .get().addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        if(!task.result.isEmpty) {
                            edtEmail.error = getString(R.string.exist_email)
                        }else{
                            val newUser = User(edtName.text.toString(), edtEmail.text.toString(), edtPass.text.toString(), null, null, null, null)
                            collectionUser.add(newUser)
                                .addOnCompleteListener { taskRegister ->
                                    if (taskRegister.isSuccessful){
                                        dialogBuilder.setPositiveButton(getString(R.string.ok)) { _, _ ->
                                            val intent = Intent(this, Login::class.java)
                                            startActivity(intent)
                                            overridePendingTransition(R.anim.out_in,R.anim.in_out)
                                        }

                                        // create dialog box
                                        val alert = dialogBuilder.create()
                                        // set title for alert dialog box
                                        alert.setTitle(getString(R.string.registed))
                                        // show alert dialog
                                        alert.show()
                                        //Redirect
                                    }else{
                                        Log.d(TAG, "Erro")
                                    }
                                }.addOnFailureListener {
                                    Log.w(TAG, "Erro")
                                }
                        }
                    }
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun goLogin(view: View) {
        findViewById<AppCompatButton>(R.id.sign_up)
            .setBackgroundColor(getColor(R.color.cinza_transparent_45))
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.O)
    fun showPass(view: View) {
        val show = findViewById<ImageView>(R.id.show)
        if(show.tooltipText.toString() == getString(R.string.show)){
            show.background = getDrawable(R.drawable.ic_hide)
            show.tooltipText = getString(R.string.hide)
            edtPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            edtConfirm.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }else{
            show.background = getDrawable(R.drawable.ic_show)
            show.tooltipText = getString(R.string.show)
            edtPass.transformationMethod = PasswordTransformationMethod.getInstance()
            edtConfirm.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }
}