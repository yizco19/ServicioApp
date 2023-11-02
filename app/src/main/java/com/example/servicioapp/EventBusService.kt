package com.example.servicioapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.greenrobot.eventbus.EventBus

class EventBusService : Service() {
    private var enable: Boolean = false;
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        this.run()
        return START_NOT_STICKY
    }
    private fun run() {
        enable = true
        Thread {
            var contador=0
            while (enable) {
                try {
                    Thread.sleep(2000)
                    contador++;
//se envia un evento
                    EventBus.getDefault().post(EjEvento(Persona("Yang",contador)))
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }
    override fun onDestroy() {
        enable=false
        super.onDestroy()
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}