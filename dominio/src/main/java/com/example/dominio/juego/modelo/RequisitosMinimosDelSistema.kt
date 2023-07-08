package com.example.dominio.juego.modelo

data class RequisitosMinimosDelSistema(
    val sistemaOperativo: String,
    val procesador: String,
    val memoria: String,
    val grafica: String,
    val almacenamiento: String,
)
