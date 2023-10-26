package com.example.servicioapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class PrimerPlano : Service() {
    private val numPrimos= Integer.MAX_VALUE/40000
    private val CHANNEL_ID = "ForegroundServiceChannel"
    private lateinit var manager: NotificationManager
    //para controlar el hilo
    private var enable: Boolean = false;
    override fun onCreate() {
        super.onCreate()
    }
    //notificacion que aparece en la parte superior
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this,
            MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_MUTABLE or
                    PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification: Notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Ejemplo Servicio primer plano")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_message)
                .setContentIntent(pendingIntent)
                .build()
        startForeground(102, notification)
        this.run()

        return START_NOT_STICKY
    }
    //hilo que escribe en el log
    private fun run() {
        enable = true
        Thread {
            while (enable) {
                Log.e("Service", "Service is running...")
                var listaPrimos=findPrimeNumbers(numPrimos)
                Log.i("ListaPrimos",listaPrimos.toString())
                try {
                    Thread.sleep(2000)
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
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    private fun isPrime(number: Int, divisor: Int = number - 1): Boolean {
        if (number <= 1 || divisor == 1) {
            return true
        }
        if (number % divisor == 0) {
            return false
        }
        return isPrime(number, divisor / 2)
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