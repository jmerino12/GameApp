package com.example.infraestructura.juego.anticorrupcion

import com.example.dominio.juego.modelo.CapturaDePantalla
import com.example.dominio.juego.modelo.Juego
import com.example.dominio.juego.modelo.JuegoBase
import com.example.dominio.juego.modelo.JuegoDetalle
import com.example.dominio.juego.modelo.RequisitosMinimosDelSistema
import com.example.infraestructura.juego.clienteHttp.dto.CapturaDePantallaDto
import com.example.infraestructura.juego.clienteHttp.dto.JuegoDetalleDto
import com.example.infraestructura.juego.clienteHttp.dto.JuegoDto
import com.example.infraestructura.juego.clienteHttp.dto.RequisitosMinimosDelSistemaDto
import com.example.infraestructura.juego.persistencia.entidad.JuegoEntidad


class TraductorDeJuegos {

    companion object {

        fun desdeEntidadHaciaModelo(juegoEntidad: JuegoEntidad): Juego {
            return Juego(
                identificador = juegoEntidad.identificador,
                titulo = juegoEntidad.titulo,
                genero = juegoEntidad.genero,
                descripcionCorta = juegoEntidad.descripcion,
                miniatura = juegoEntidad.miniatura,
                plataforma = juegoEntidad.plataforma,
                editor = juegoEntidad.editor,
                fechaDeLanzamiento = juegoEntidad.fechaDeLanzamiento,
                favorito = juegoEntidad.favorito
            )
        }

        fun desdeJuegoDetalleAJuego(juegoDetalle: JuegoDetalle): Juego {
            return Juego(
                identificador = juegoDetalle.identificador,
                titulo = juegoDetalle.titulo,
                genero = juegoDetalle.genero,
                descripcionCorta = juegoDetalle.descripcionCorta,
                miniatura = juegoDetalle.miniatura,
                plataforma = juegoDetalle.plataforma,
                editor = juegoDetalle.editor,
                fechaDeLanzamiento = juegoDetalle.fechaDeLanzamiento,
                favorito = juegoDetalle.favorito
            )
        }

        fun desdeModeloHaciaEntidad(juego: JuegoBase): JuegoEntidad {
            return JuegoEntidad(
                identificador = juego.identificador,
                titulo = juego.titulo,
                genero = juego.genero,
                descripcion = juego.descripcionCorta,
                miniatura = juego.miniatura,
                plataforma = juego.plataforma,
                editor = juego.editor,
                fechaDeLanzamiento = juego.fechaDeLanzamiento,
                favorito = juego.favorito
            )
        }

        fun desdeDtoHaciaModelo(juegoDto: JuegoDto): Juego {
            return Juego(
                identificador = juegoDto.identificador,
                titulo = juegoDto.titulo,
                genero = juegoDto.genero,
                descripcionCorta = juegoDto.descripcionCorta,
                miniatura = juegoDto.miniatura,
                plataforma = juegoDto.plataforma,
                editor = juegoDto.editor,
                fechaDeLanzamiento = juegoDto.fechaDeLanzamiento,
                favorito = false
            )
        }

        fun desdeDtoDetalleHaciaModelo(juegoDetalleDto: JuegoDetalleDto): JuegoDetalle {
            return JuegoDetalle(
                identificador = juegoDetalleDto.identificador,
                titulo = juegoDetalleDto.titulo,
                genero = juegoDetalleDto.genero,
                descripcionCorta = juegoDetalleDto.descripcionCorta,
                descripcion = juegoDetalleDto.descripcion,
                miniatura = juegoDetalleDto.miniatura,
                plataforma = juegoDetalleDto.plataforma,
                editor = juegoDetalleDto.editor,
                fechaDeLanzamiento = juegoDetalleDto.fechaDeLanzamiento,
                requisitosMinimosDelSistema = desdeDtoRequisitosMinimoDelSistemaHaciaModelo(
                    juegoDetalleDto.requisitosMinimosDelSistemaDto
                ),
                capturaDePantalla = juegoDetalleDto.capturasDePantalla.map {
                    desdeDtoCapturasDePantallaHaciaModelo(
                        it
                    )
                },
                urlJuego = juegoDetalleDto.urlJuego,
                favorito = false
            )
        }

        fun desdeDtoRequisitosMinimoDelSistemaHaciaModelo(requisitos: RequisitosMinimosDelSistemaDto): RequisitosMinimosDelSistema {
            return RequisitosMinimosDelSistema(
                sistemaOperativo = requisitos.sistemaOperativo,
                procesador = requisitos.procesador,
                memoria = requisitos.memoria,
                grafica = requisitos.grafica,
                almacenamiento = requisitos.almacenamiento
            )
        }

        fun desdeDtoCapturasDePantallaHaciaModelo(captura: CapturaDePantallaDto): CapturaDePantalla {
            return CapturaDePantalla(
                identificador = captura.identificador,
                imagen = captura.imagen
            )
        }
    }
}