package com.example.infraestructura.juego.anticorrupcion

import com.example.dominio.juego.modelo.Juego
import com.example.infraestructura.juego.clienteHttp.dto.JuegoDto
import com.example.infraestructura.juego.persistencia.entidad.JuegoEntidad


class TraductorDeJuegos {

    companion object {

        fun desdeEntidadHaciaModelo(juegoEntidad: JuegoEntidad): Juego {
            return Juego(
                identificador = juegoEntidad.identificador,
                titulo = juegoEntidad.titulo,
                genero = juegoEntidad.genero,
                descripcion = juegoEntidad.descripcion,
                miniatura = juegoEntidad.miniatura,
                plataforma = juegoEntidad.plataforma,
                editor = juegoEntidad.editor,
                fechaDeLanzamiento = juegoEntidad.fechaDeLanzamiento
            )
        }

        fun desdeModeloHaciaEntidad(juego: Juego): JuegoEntidad {
            return JuegoEntidad(
                identificador = juego.identificador,
                titulo = juego.titulo,
                genero = juego.genero,
                descripcion = juego.descripcion,
                miniatura = juego.miniatura,
                plataforma = juego.plataforma,
                editor = juego.editor,
                fechaDeLanzamiento = juego.fechaDeLanzamiento
            )
        }

        fun desdeDtoHaciaModelo(juegoDto: JuegoDto): Juego {
            return Juego(
                identificador = juegoDto.identificador,
                titulo = juegoDto.titulo,
                genero = juegoDto.genero,
                descripcion = juegoDto.descripcionCorta,
                miniatura = juegoDto.miniatura,
                plataforma = juegoDto.plataforma,
                editor = juegoDto.editor,
                fechaDeLanzamiento = juegoDto.fechaDeLanzamiento
            )
        }
    }
}