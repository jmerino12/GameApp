package com.example.dominio.juego.modelo

import com.example.dominio.juego.excepciones.ExcepcionDeParametroVacio

abstract class JuegoBase(
    val identificador: Int,
    val titulo: String,
    val genero: String,
    val descripcionCorta: String,
    val miniatura: String,
    val plataforma: String,
    val editor: String,
    val fechaDeLanzamiento: String,
) {
    init {
        validarCampos()
    }

    private fun validarCampos() {
        if (titulo.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "titulo")
        if (genero.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "genero")
        if (descripcionCorta.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "descripcion")
        if (miniatura.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "miniatura")
        if (plataforma.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "plataforma")
        if (editor.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "editor")
        if (fechaDeLanzamiento.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "fechaDeLanzamiento")
    }
}
