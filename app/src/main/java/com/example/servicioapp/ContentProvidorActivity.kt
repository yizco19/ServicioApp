package com.example.servicioapp

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.provider.Telephony
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ContentProvidorActivity : AppCompatActivity() {
    private val READ_CALL_LOG_CODE: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_providor)

        // Botón para compruba si tiene permisos y se muestra la ultima llamada
        findViewById<Button>(R.id.b_proviver).setOnClickListener {
            // Verificar si tiene permisos

            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_CALL_LOG
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                muestraUltimaLlamada()
            } else {
                // Solicitar permisos
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf((android.Manifest.permission.READ_CALL_LOG)),
                    this.READ_CALL_LOG_CODE
                )
            }

        }
        //Botón para salir la aplicación
        findViewById<Button>(R.id.salirProvidor).setOnClickListener {
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == this.READ_CALL_LOG_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                muestraUltimaLlamada()
            } else {
                // Mostrar mensaje de permisos denegados
                Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("Range")
    private fun muestraUltimaLlamada() {
        val cr = applicationContext.contentResolver
        // se muestra la ultima llamada
        val c = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, null)
        if (c != null) {
            c.moveToFirst()
                val number = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER))
                val name = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME))
                val date = c.getString(c.getColumnIndex(CallLog.Calls.DATE)).toString()
                val duration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION))
                val textView = findViewById<TextView>(R.id.textView)
                textView.text = "Numero : $number \n Nombre :$name  \n Fecha :$date \n Duración :$duration segundos"

            c.close()
        }

    }
}