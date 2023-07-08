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
    favorito: Boolean
) : JuegoBase(
    identificador,
    titulo,
    genero,
    descripcionCorta,
    miniatura,
    plataforma,
    editor,
    fechaDeLanzamiento,
    favorito
) {
    fun copy(
        identificador: Int = this.identificador,
        titulo: String = this.titulo,
        genero: String = this.genero,
        descripcionCorta: String = this.descripcionCorta,
        descripcion: String = this.descripcion,
        miniatura: String = this.miniatura,
        plataforma: String = this.plataforma,
        editor: String = this.editor,
        fechaDeLanzamiento: String = this.fechaDeLanzamiento,
        requisitosMinimosDelSistema: RequisitosMinimosDelSistema = this.requisitosMinimosDelSistema,
        capturaDePantalla: List<CapturaDePantalla> = this.capturaDePantalla,
        urlJuego: String = this.urlJuego,
        favorito: Boolean = this.favorito
    ): JuegoDetalle {
        return JuegoDetalle(
            identificador,
            titulo,
            genero,
            descripcionCorta,
            descripcion,
            miniatura,
            plataforma,
            editor,
            fechaDeLanzamiento,
            requisitosMinimosDelSistema,
            capturaDePantalla,
            urlJuego,
            favorito
        )
    }
}


