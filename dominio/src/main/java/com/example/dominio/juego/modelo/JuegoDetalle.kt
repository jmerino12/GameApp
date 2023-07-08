package com.example.dominio.juego.modelo

class JuegoDetalle(
    identificador: Int,
    titulo: String,
    genero: String,
    descripcionCorta: String,
    val descripcion: String,
    miniatura: String,
    plataforma: String,
    editor: String,
    fechaDeLanzamiento: String,
    val requisitosMinimosDelSistema: RequisitosMinimosDelSistema,
    val capturaDePantalla: List<CapturaDePantalla>,
    val urlJuego: String,
) : JuegoBase(
    identificador,
    titulo,
    genero,
    descripcionCorta,
    miniatura,
    plataforma,
    editor,
    fechaDeLanzamiento
)


