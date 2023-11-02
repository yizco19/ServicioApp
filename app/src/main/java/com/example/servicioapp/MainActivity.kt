package com.example.servicioapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.Random

class MainActivity : AppCompatActivity() {
    private var enabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonPrimerPlano = findViewById<Button>(R.id.calcular)

        botonPrimerPlano.setOnClickListener {
//variable para cambiar el texto
            if(!enabled) {
                //es un intetn y se le pasa el mensaje cómo parámetro
                val serviceIntent = Intent(this, PrimerPlano::class.java)
                serviceIntent.putExtra("inputExtra", "Se tiene que avisar de que está en primer plano")
                        //se inicia en primer plano
                        ContextCompat.startForegroundService(this, serviceIntent)
                botonPrimerPlano.text="Stop"
                enabled=true
            }
            else {
                botonPrimerPlano.text="Start"
                val serviceIntent = Intent(this, PrimerPlano::class.java)
                //se para
                stopService(serviceIntent)
            }
        }

        val botonColor = findViewById<Button>(R.id.color)
        botonColor.setOnClickListener {
            val rnd = Random()
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            botonColor.setBackgroundColor(color)
        }

        val botonSegundoPlano = findViewById<Button>(R.id.calcular2)
        botonSegundoPlano.setOnClickListener{
            startService(Intent(this,SegundoPlano::class.java))
        }
        val botonIntentServicio=findViewById<Button>(R.id.intentServicioBoton)
        botonIntentServicio.setOnClickListener{
            val intent = Intent(this@MainActivity,
                EjIntentService::class.java)
            startService(intent)
        }
        val botonVincular=findViewById<Button>(R.id.abrirVincular)
        botonVincular.setOnClickListener{
            val intent=Intent(this,VincularActivity::class.java)
            startActivity(intent)

        }
        val botonWorkManager=findViewById<Button>(R.id.workManagerBoton)
        botonWorkManager.setOnClickListener{
            val intent=Intent(this,WorkActivity::class.java)
            startActivity(intent)

        }
    }
}
