package com.example.servicioapp

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class BroadCastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broad_cast)
        //registrar Broadcast
        val receiver = Receiver()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_BATTERY_LOW)
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        //se registra a nivel de aplicación
        applicationContext.registerReceiver(receiver,filter)

        //Botón para salir la aplicación
        findViewById<Button>(R.id.salir).setOnClickListener {
            //desregistrar Broadcast
            applicationContext.unregisterReceiver(receiver)
            finish()
        }
        val intent=Intent()
        val texto=findViewById<TextView>(R.id.texto)
        texto.text=intent.action



    }
}