package com.example.servicioapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class SegundoPlano : Service(){
    //para controlar el hilo
    private var enable: Boolean = false;
    override fun onStartCommand(intent: Intent, flags: Int, startId:
    Int): Int {
        //se inicia le hilo
        this.run()
        return START_NOT_STICKY
    }
    private fun run() {
        enable = true
        Thread {
            var contador=0
            while (enable) {
                Log.e("Service", "Background service is running..."+contador)
                    try {
                        Thread.sleep(2000)
                        contador++;
                        if(contador>10){
                            //se para y se llama a destuir
                            stopSelf()
                        }
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