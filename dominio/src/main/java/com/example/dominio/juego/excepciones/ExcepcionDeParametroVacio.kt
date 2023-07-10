package com.example.dominio.juego.excepciones


class ExcepcionDeParametroVacio(
    private val parametro: String,
    message: String = "El parametro $parametro no puede ser vacio"
) : Exception(message)