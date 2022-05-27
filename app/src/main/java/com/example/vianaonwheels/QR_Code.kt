package com.example.vianaonwheels

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
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

        val dadosNoQR = intent.getStringExtra(EXTRA_TICKET).toString()
        imageView = findViewById(R.id.imageView)
        val multiFormatWriter = MultiFormatWriter();
        try {
            val hintMap = mapOf(EncodeHintType.MARGIN to 1)
            val bitMatrix: BitMatrix =
                multiFormatWriter.encode(dadosNoQR, BarcodeFormat.QR_CODE, 300, 300, hintMap)
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

    }
}