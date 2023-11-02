package com.example.servicioapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class WorkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)

        val botonWorkManager=findViewById<Button>(R.id.runWorkManagerBoton)
        botonWorkManager.setOnClickListener{
            startService(Intent(this, EventBusService::class.java))
            EventBus.getDefault().register(this);
            val workManager: WorkManager = WorkManager.getInstance(this)
            val peticion: WorkRequest = OneTimeWorkRequestBuilder<EjWorker>().build()
            val id=peticion.id
            workManager.getWorkInfoByIdLiveData(id)
                .observe(this, Observer { info ->
                    if (info != null && info.state.isFinished) {
                        val resultado = info.outputData.getString("vuelta")
                        Log.d("Worker", resultado.toString())
                    }
                })
            workManager.enqueue(peticion)
            EventBus.getDefault().unregister(this);
        }
        val botonSalir=findViewById<Button>(R.id.salir)
        botonSalir.setOnClickListener {
            finish()
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EjEvento) {
        Log.d("Bus Evento", event.getMensaje()?.alearorio.toString())
    }
}