package com.example.servicioapp

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class SegundoPlano : Service() {
    //para controlar el hilo
    private var enable: Boolean = false;
    private var numero: Int = Int.MAX_VALUE / 40000

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        this.run()
        return START_STICKY
    }

    private fun run() {
        enable = true
        Thread {
            val primos = calcularPrimos(numero)
            while (enable) {
                // Mostrar los resultados en el log
                Log.i("SegundoPlano ","Calculando primos...")
                Log.i("SegundoPlano ",primos.toString())
                try {
                    Thread.sleep(5000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }



    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
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