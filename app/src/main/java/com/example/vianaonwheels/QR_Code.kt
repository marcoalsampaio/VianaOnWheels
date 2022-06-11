package com.example.vianaonwheels

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vianaonwheels.adapters.ToUseAdapter
import com.example.vianaonwheels.models.ToUse
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import kotlin.math.log

class QR_Code : AppCompatActivity() {
    private var imageView: ImageView? = null
    //TopBar
    private lateinit var userEmail : String
    private lateinit var nDrawerLayout: DrawerLayout;
    private lateinit var navView: NavigationView;
    private lateinit var tituloPagina: TextView;
    private lateinit var TextView: TextView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)

        nDrawerLayout = findViewById(R.id.drawerLayout)
        navView= findViewById(R.id.navView)

        tituloPagina= findViewById(R.id.tituloPagina)
        tituloPagina.setText(R.string.TicketQR)




        userEmail = intent.getStringExtra(EXTRA_USEREMAIL).toString()
        db= FirebaseFirestore.getInstance()
        TextView= findViewById(R.id.tv2)
        val dadosNoQR = intent.getStringExtra("dadosQR").toString()
        val collectionTicket = db.collection("Tickets")
        collectionTicket.document(dadosNoQR).get().addOnSuccessListener { document ->
            if (document != null){
                imageView = findViewById(R.id.imageView)
                val multiFormatWriter = MultiFormatWriter();
                try {
                    val hintMap = mapOf(EncodeHintType.MARGIN to 1)
                    val bitMatrix: BitMatrix =
                        multiFormatWriter.encode(document.data.toString(), BarcodeFormat.QR_CODE, 300, 300, hintMap)
                    val bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.RGB_565)
                    for (x in 0..299) {
                        for (y in 0..299) {
                            bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                        }
                    }
                    imageView!!.setImageBitmap(bitmap)
                    TextView.text =document.data?.get("origin").toString()+" - "+document.data?.get("destiny").toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                Log.d(TAG, "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }
        collectionTicket.document(dadosNoQR).update("used", "s").addOnCompleteListener { taskRegister ->
            if (taskRegister.isSuccessful){
                Toast.makeText(this, getString(R.string.ok), Toast.LENGTH_LONG).show()
            }else{
                Log.d(TAG, "Erro")
            }
        }.addOnFailureListener {
            Log.w(TAG, "Erro")
        }
    }
    fun aboutUS(view: View) {
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
            .addOnSuccessListener { Log.d(TAG, getString(R.string.account_deleted))
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
            .addOnFailureListener { e -> Log.w(TAG, getString(R.string.error_deleting), e)
                Toast.makeText(this,  getString(R.string.error_deleting), Toast.LENGTH_LONG).show()}
    }
    fun logout(view: View) {
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out)
        finish()
    }
    fun backIcon(view: View) {
        val intent = Intent(this, ToUseActivity::class.java).apply {
            putExtra(EXTRA_USEREMAIL, userEmail)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.out_in,R.anim.in_out)
    }


    fun menuIcon(view: View) {
        nDrawerLayout.openDrawer(navView)
    }
}

