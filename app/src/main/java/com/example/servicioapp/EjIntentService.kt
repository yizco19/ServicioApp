package com.example.servicioapp

import android.app.IntentService
import android.content.Intent
import android.util.Log


class EjIntentService : IntentService("EjemploIntentService") {
    private val numPrimos= Integer.MAX_VALUE/40000
    fun EjemploIntentService(){
    }
    override fun onHandleIntent(intent: Intent?) {
        //para ver cómo se encolan las peticiones
        Thread.sleep(5000)
        Log.d("IntentService","Llamando al intent")
        var listaPrimos=findPrimeNumbers(numPrimos)
        Log.i("ListaPrimos",listaPrimos.toString())
    }

    private fun isPrime(number :Int): Boolean {
        for (i in 2 until number) {
            if (number % i == 0) return false
        }
        return true
    }

    // Función para encontrar números primos
    private fun findPrimeNumbers(n: Int): ArrayList<Int> {
        val primeNumbers = ArrayList<Int>()
        for (i in n - 1 downTo 1) {
            if (isPrime(i)) {
                primeNumbers.add(i)
            }
        }
        return primeNumbers
    }

}