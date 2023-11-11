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
import java.text.SimpleDateFormat
import java.util.Date

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
            ) {// si tiene permisos muestra la ultima llamada
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


    @SuppressLint("Range") //SuppressLint para el manejo de excepciones de llamadas sin datos
    private fun muestraUltimaLlamada() {
        val cr = applicationContext.contentResolver
        // obtener la llamada mas reciente
        val c = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, null)
        if (c != null) {
            val textView = findViewById<TextView>(R.id.textView)
            // si no tiene ninguna llamada
            if (c.count == 0) {
                textView.text = "No hay llamadas"
            }else{

                c.moveToLast()
                val number = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER))
                val name = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME))
                val date = c.getString(c.getColumnIndex(CallLog.Calls.DATE))
                //convertir la fecha
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                val fechaFormateada = sdf.format(Date(date.toLong()))

                val duration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION))

                textView.text = " Numero : $number \n Nombre :$name  \n Fecha :$fechaFormateada \n Duración :$duration segundos"
            }


            c.close()
        }
    }
}