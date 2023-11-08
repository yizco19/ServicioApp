package com.example.servicioapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import org.greenrobot.eventbus.Subscribe
import java.util.Random

class MainActivity : AppCompatActivity() {
    private var enabledPrimerPlano = false
    private var enabledSegundoPlano = false
    private var enabledIntentService = false
    private val primo=Integer.MAX_VALUE/40000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botón para calcular los números primos en primer plano
        findViewById<Button>(R.id.primerPlano).setOnClickListener {
            if (!enabledPrimerPlano) {
                val intent = Intent(this, PrimerPlano::class.java)
                intent.putExtra("numero", primo)
                startService(intent)
                enabledPrimerPlano= true
            }else{
                val intent = Intent(this, PrimerPlano::class.java)
                stopService(intent)
                enabledPrimerPlano= false
            }

        }

        // Botón para calcular los números primos en segundo plano
        findViewById<Button>(R.id.segundoPlano).setOnClickListener {
            if (!enabledSegundoPlano) {
                val intent = Intent(this, SegundoPlano::class.java)

                // Iniciar el servicio
                startService(intent)

                enabledPrimerPlano= true
            }else{
                val intent = Intent(this, SegundoPlano::class.java)
                stopService(intent)
                enabledPrimerPlano= false
            }

        }

        // Botón para calcular los números primos en IntentService
        findViewById<Button>(R.id.intentService).setOnClickListener {
            if (enabledIntentService){
                // Crear un Intent
                val intent = Intent(this, PrimosIntentService::class.java)

                // Pasar el número a calcular
                intent.putExtra("numero", Integer.MAX_VALUE / 40000)

                // Iniciar el servicio
                startService(intent)
            }else{
                val intent = Intent(this, PrimosIntentService::class.java)
                stopService(intent)
                enabledIntentService= false
            }
        }

        // Botón para calcular los primos en WorkManager
        findViewById<Button>(R.id.workManager).setOnClickListener {

            val workerManager: WorkManager = WorkManager.getInstance(this)
            val petition :WorkRequest = OneTimeWorkRequestBuilder<PrimeWorker>().build()
            val id=petition.id
            workerManager.getWorkInfoByIdLiveData(id).observe(this) {
                if(it!=null && it.state.isFinished){
                    val resultado=it.outputData.getString("vuelta")
                    Log.d("Worker", resultado.toString())
                }

            }
            workerManager.enqueue(petition)
        }

        // Botón para cambiar el color del botón
        findViewById<Button>(R.id.cambiarColor).setOnClickListener {
            val rnd = Random()

            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

            findViewById<Button>(R.id.cambiarColor).setBackgroundColor(color)
        }

    }


}

