package com.example.servicioapp

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class Vinculado : Service() {
    //para controlar el hilo
    private var enable: Boolean = false;
    //para ver por donde va
    private var contador=0;
    //interfaz
    private val ejemploBinder: IBinder? = EjemploBind()

    private val numPrimos= Integer.MAX_VALUE/40000
    override fun onCreate() {
        super.onCreate()

    }
    override fun onStartCommand(intent: Intent, flags: Int, startId:
    Int): Int {
        //se inicia le hilo
        this.run()
        return START_NOT_STICKY
    }
    //hilo que escribe en el log
    private fun run() {
        enable = true
        Thread {
            while (enable) {
                Log.e("Service", "Service is running...")
                Log.e("Primos",findPrimeNumbers(numPrimos).toString())
                try {
                    Thread.sleep(2000)
                    contador++
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }
    override fun onDestroy() {
        //parar el hilo
        enable = false
        super.onDestroy()
    }
    //servicio sencillo
    public fun getContador():Int{
        return this.contador
    }
    override fun onBind(p0: Intent?): IBinder? {
        return this.ejemploBinder
    }
    inner class EjemploBind: Binder() {
        fun getService(): Vinculado=this@Vinculado
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