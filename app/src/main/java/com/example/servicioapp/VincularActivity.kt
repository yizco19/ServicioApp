package com.example.servicioapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button

class VincularActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vincular)
        val botonVincular=findViewById<Button>(R.id.vincularBoton)
        botonVincular.setOnClickListener{
                val intent = Intent(this, Vinculado::class.java)
                startService(intent)
                bindService(intent, this.connection, BIND_AUTO_CREATE)
            }
        val botonDesvincular=findViewById<Button>(R.id.desvincularBoton)
        botonDesvincular.setOnClickListener{
            unbindService(connection)
        }
        val botonSalir=findViewById<Button>(R.id.salir)
        botonSalir.setOnClickListener {
            finish()
        }

    }
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName,
                                        service: IBinder
        ) {
            val binder = service as Vinculado.EjemploBind
            var servicio = binder.getService()
        }
        
        override fun onServiceDisconnected(arg0: ComponentName) {
        }
    }


}