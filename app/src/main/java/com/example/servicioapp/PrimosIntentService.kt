package com.example.servicioapp

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log

// TODO: Rename actions, choose action names that describe tasks that this
// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
private const val ACTION_FOO = "com.example.servicioapp.action.FOO"
private const val ACTION_BAZ = "com.example.servicioapp.action.BAZ"

// TODO: Rename parameters
private const val EXTRA_PARAM1 = "com.example.servicioapp.extra.PARAM1"
private const val EXTRA_PARAM2 = "com.example.servicioapp.extra.PARAM2"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.

 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.

 */
class PrimosIntentService : IntentService("PrimosIntentService") {
    private var numero = 0
    //para controlar el hilo
    private var enable: Boolean? = false
    override fun onHandleIntent(intent: Intent?) {
        this.numero = intent?.getIntExtra("numero", 0) ?: 0

        // Se inicia el hilo
        this.run()
    }

    private fun run() {
        enable = true
        Thread {
            val primos = calcularPrimos(numero)
            while (enable == true) {
                Log.d("IntentService", "Calculando primos...")
                Log.d("IntentService", "Primos calculados: $primos")
                try {
                    Thread.sleep(5000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

        }.start()
    }
    private fun calcularPrimos(numero: Int): List<Int> {
        val primos = mutableListOf<Int>()

        for (i in 2 until numero) {
            var esPrimo = true

            for (j in 2 until i) {
                if (i % j == 0) {
                    esPrimo = false
                    break
                }
            }

            if (esPrimo) {
                primos.add(i)
            }
        }

        return primos
    }
}