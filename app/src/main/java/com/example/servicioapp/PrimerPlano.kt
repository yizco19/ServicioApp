package com.example.servicioapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class PrimerPlano : Service() {
    private val CHANNEL_ID = "ForegroundServiceChannel"
    private lateinit var manager: NotificationManager
    private var numero = 0
    //para controlar el hilo
    private var enable: Boolean? = false
    override fun onCreate() {
        super.onCreate()

    }
    //notificacion que aparece en la parte superior
    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

         this.numero = intent?.getIntExtra("numero", 0) ?: 0


        // Crear una notificación
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Primer plano")
            .setContentText("Calculando primos...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
        // Iniciar el servicio
        startForeground(102, notification)

        // Se inicia el hilo
        this.run()

        return START_STICKY
    }

    private fun run() {
        enable = true
        Thread {
            val primos = calcularPrimos(numero)
            while (enable == true) {
                Log.d("PrimerPlano", "Calculando primos...")
                Log.d("PrimerPlano", "Primos calculados: $primos")
                try {
                    Thread.sleep(5000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        enable = false
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
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
