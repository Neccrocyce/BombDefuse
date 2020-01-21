package com.mai.bombdefuse

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.zxing.BarcodeFormat
import me.dm7.barcodescanner.zxing.ZXingScannerView
import com.google.zxing.Result
import kotlinx.android.synthetic.main.defuse_setup.*

/**
 * @see https://codinginfinite.com/qrcode-generator-and-reader-android-example/
 */
class DefuseQRCodeScanner : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private val HUAWEI = "huawei"
    private val MY_CAMERA_REQUEST_CODE = 6515
    private var isSetup = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.defuse_setup)

        isSetup = intent.getBooleanExtra("isSetup",false)

        setScannerProperties()
    }

    private fun setScannerProperties() {
        qrCodeScanner.setFormats(listOf(BarcodeFormat.QR_CODE))
        qrCodeScanner.setAutoFocus(true)
        qrCodeScanner.setLaserColor(R.color.colorAccent)
        qrCodeScanner.setMaskColor(R.color.colorAccent)
        if (Build.MANUFACTURER.equals(HUAWEI, ignoreCase = true))
            qrCodeScanner.setAspectTolerance(0.5f)
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                        MY_CAMERA_REQUEST_CODE)
                return
            }
        }
        qrCodeScanner.startCamera()
        qrCodeScanner.setResultHandler(this)
    }


    override fun handleResult(p0: Result?) {
        if (p0 != null) {
            if (isSetup) {
                val key = IntArray(p0.text.length)
                for ((i, character) in p0.text.toCharArray().withIndex()) {
                    key[i] = Integer.parseInt("" + character)
                }
                Settings.setKey(key)
                startActivity(Intent(this, DefuseReady::class.java))
            } else {
                val intent = Intent(this, DefuseActive::class.java).apply {
                    putExtra("code", p0.text)
                }
                startActivity(intent)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        qrCodeScanner.stopCamera()
    }

    /*
    private fun resumeCamera() {
        Toast.LENGTH_LONG
        val handler = Handler()
        handler.postDelayed({ qrCodeScanner.resumeCameraPreview(this@DefuseQRCodeScanner) }, 2000)
    }*/
}