package com.example.servicioapp

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class PrimeWorker (context: Context, workerParams: WorkerParameters) :
Worker(context, workerParams){
    override fun doWork(): Result {
        // Registrar el Worker en el bus de eventos
        EventBus.getDefault().register(this)
        metodo()
        val vuelta= workDataOf(Pair("vuelta" ,"vuelta"))
        return Result.success(vuelta)
    }

    private fun metodo() {
        Thread.sleep(5000)
        Log.i("PrimeWorker", "Calculando primos...")
        // Calcular los números primos
        val numbers = calcularPrimos(Integer.MAX_VALUE / 40000)
        Log.i("PrimeWorker", "Primos calculados: $numbers")
        // Publicar un evento
        EventBus.getDefault().post(PrimeEvento(numbers))

        // Mostrar los resultados en el log
        Log.d("PrimeWorker", numbers.toString())
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

    @Subscribe
    fun onPrimeEvento(event: PrimeEvento) {
        // Obtener los números primos
        val numbers = event.numbers
        Log.i("EventBus", numbers.toString())
    }

}
