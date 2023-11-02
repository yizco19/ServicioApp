package com.example.servicioapp

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.workDataOf
import androidx.work.WorkerParameters

class EjWorker (context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    private val numPrimos= Integer.MAX_VALUE/40000
    override fun doWork(): Result {

        metodoejemplo()
        val vuelta= workDataOf(Pair("vuelta","otroobjecto"))
        return Result.success(vuelta)
    }
    private fun metodoejemplo() {
        Thread.sleep(3000)
        Log.d("Worker", "Se ha ejecutado")
        Log.d("Worker",findPrimeNumbers(numPrimos).toString())
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