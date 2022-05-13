package com.example.vianaonwheels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class SignUp : AppCompatActivity() {

    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtPass: EditText
    lateinit var edtConfirm: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        edtName = findViewById(R.id.sign_up_name)
        edtEmail = findViewById(R.id.edt_insert_email_sign)
        edtPass = findViewById(R.id.edt_insert_pass_sign)
        edtConfirm = findViewById(R.id.edt_insert_pass_confirm)

    }

    private fun  checkAllFieldsInput(): Boolean { //Strings

        if (edtName.length() == 0 && edtEmail.length() == 0 && edtPass.length() == 0 && edtConfirm.length() == 0){
            edtName.error = "Name is required";
            edtEmail.error = "Email is required";
            edtPass.error = "Password is required";
            edtConfirm.error = "Password is required";
        }

        if (edtName.length() == 0) {
            edtName.error = "Name is required";
            return false;
        }

        if (edtEmail.length() == 0) {
            edtEmail.error = "Email is required";
            return false;
        }

        if (edtPass.length() == 0) {
            edtPass.error = "Password is required";
            return false;
        } else if (edtPass.length() < 8) {
            edtPass.error = "Password must be minimum 8 characters";
            return false;
        }

        if(edtConfirm.length() == 0) {
            edtConfirm.error = "Password is required";
        }else if (edtConfirm != edtPass) {
            edtConfirm.error = "Password is not the same";
            edtPass.error = "Password is not the same";
            return false;
        }
        // after all validation return true.
        return true;
    }

    fun sign_up(view: View) {
        checkAllFieldsInput()
    }
}