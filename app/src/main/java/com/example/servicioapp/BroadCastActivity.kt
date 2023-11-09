package com.example.servicioapp

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class BroadCastActivity : AppCompatActivity() {
    lateinit var receiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broad_cast)
        //registrar Broadcast
        /*val receiver = Receiver()
        val filter = IntentFilter()
        // añadir las acciones de low  battery y modo aereo
        filter.addAction(Intent.ACTION_BATTERY_LOW)
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        //se registra a nivel de aplicación
        applicationContext.registerReceiver(receiver,filter)*/

        // crear BroadcastReceiver de nivel de aplicación
        receiver=object :BroadcastReceiver(){
                override fun onReceive(context: android.content.Context?, intent: android.content.Intent?) {
                StringBuffer().apply {
                    append("Action: ${intent?.action}\n")
                    append("URI: ${intent?.toUri(Intent.URI_INTENT_SCHEME)}\n")
                    toString().also { log ->
                        // se muestra un mensaje
                        val texto=findViewById<TextView>(R.id.texto)
                        texto.text=""
                        if(intent?.action.equals(Intent.ACTION_BATTERY_LOW)){
                            texto.text="Bateria baja"
                        }
                        if(intent?.action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED) && intent!!.getBooleanExtra("state",true)){
                            texto.text=texto.text.toString()+"Entra en modo aéreo"
                        }
                    }
                }
            }
        }
        // IntentFilter para registrar Broadcast de nivel de bateria y modo aéreo
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_BATTERY_LOW)
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
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