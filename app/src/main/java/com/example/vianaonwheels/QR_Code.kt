package com.example.vianaonwheels

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vianaonwheels.adapters.ToUseAdapter
import com.example.vianaonwheels.models.ToUse
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

class QR_Code : AppCompatActivity() {
    private var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)
        db= FirebaseFirestore.getInstance()
        val dadosNoQR = intent.getStringExtra(EXTRA_TICKET).toString()

        db.collection("Tickets").document(dadosNoQR).get().addOnSuccessListener { document ->
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
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                Log.d(TAG, "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }

    }
}

/*"""
                                |Price:${ticketAPI.price};
                                |Origin:${ticketAPI.origin};
                                |Destiny:${ticketAPI.destiny};
                                |Company:${ticketAPI.company};
                                |Date:${ticketAPI.dates};
                                |Origin_Hour:${ticketAPI.origin_hour};
                                |Destiny_Hour:${ticketAPI.destiny_hour};
                                |qtd:${ticketAPI.qtd};""".trimMargin()*/