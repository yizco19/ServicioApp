package com.example.servicioapp


class Persona(var nombre:String,var alearorio:Int)
class EjEvento {
    private var mensaje: Persona? = null
    constructor(mensaje: Persona?){
        this.mensaje=mensaje
    }
    fun getMensaje(): Persona? {
        return mensaje
    }
}