package com.example.dominio.juego.modelo

import com.example.dominio.juego.excepciones.ExcepcionDeParametroVacio

data class Juego(
    val titulo: String,
    val genero: String,
    val descripcion: String,
    val miniatura: String,
    val plataforma: String,
    val editor: String,
    val fechaDeLanzamiento: String,
) {
    init {
        validateFields()
    }

    private fun validateFields() {
        if (titulo.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "titulo")
        if (genero.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "genero")
        if (descripcion.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "descripcion")
        if (miniatura.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "miniatura")
        if (plataforma.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "plataforma")
        if (editor.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "editor")
        if (fechaDeLanzamiento.isEmpty()) throw ExcepcionDeParametroVacio(parametro = "fechaDeLanzamiento")
    }
}
