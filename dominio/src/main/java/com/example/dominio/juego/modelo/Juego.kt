package com.example.dominio.juego.modelo

class Juego(
    identificador: Int,
    titulo: String,
    genero: String,
    descripcionCorta: String,
    miniatura: String,
    plataforma: String,
    editor: String,
    fechaDeLanzamiento: String,
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
        miniatura: String = this.miniatura,
        plataforma: String = this.plataforma,
        editor: String = this.editor,
        fechaDeLanzamiento: String = this.fechaDeLanzamiento,
        favorito: Boolean = this.favorito
    ): Juego {
        return Juego(
            identificador,
            titulo,
            genero,
            descripcionCorta,
            miniatura,
            plataforma,
            editor,
            fechaDeLanzamiento,
            favorito
        )
    }
}
