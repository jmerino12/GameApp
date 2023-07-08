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
