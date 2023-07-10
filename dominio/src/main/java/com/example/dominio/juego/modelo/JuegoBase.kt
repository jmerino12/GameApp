package com.example.dominio.juego.modelo

import com.example.dominio.juego.excepciones.ExcepcionDeParametroVacio
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

abstract class JuegoBase(
    val identificador: Int,
    val titulo: String,
    val genero: String,
    val descripcionCorta: String,
    val miniatura: String,
    val plataforma: String,
    val editor: String,
    fechaDeLanzamiento: String,
    var favorito: Boolean,
) {

    val fechaDeLanzamiento: String = cambiarFormatoFecha(fechaDeLanzamiento)

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

    private fun cambiarFormatoFecha(fecha: String): String {
        val formatoEntrada = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatoSalida = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        if (fecha.length == "dd/MM/yyyy".length) {
            return fecha
        }

        val fechaDate: Date = formatoEntrada.parse(fecha) ?: return fecha

        return formatoSalida.format(fechaDate)
    }
}
