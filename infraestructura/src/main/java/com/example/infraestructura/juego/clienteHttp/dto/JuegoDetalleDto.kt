package com.example.infraestructura.juego.clienteHttp.dto

import com.google.gson.annotations.SerializedName

data class JuegoDetalleDto(
    @SerializedName("id") val identificador: Int,
    @SerializedName("title") val titulo: String,
    @SerializedName("thumbnail") val miniatura: String,
    @SerializedName("status") val estado: String,
    @SerializedName("short_description") val descripcionCorta: String,
    @SerializedName("description") val descripcion: String,
    @SerializedName("game_url") val urlJuego: String,
    @SerializedName("genre") val genero: String,
    @SerializedName("platform") val plataforma: String,
    @SerializedName("publisher") val editor: String,
    @SerializedName("developer") val desarrollador: String,
    @SerializedName("release_date") val fechaDeLanzamiento: String,
    @SerializedName("freetogame_profile_url") val urlDelJuego: String,
    @SerializedName("minimum_system_requirements") val requisitosMinimosDelSistemaDto: RequisitosMinimosDelSistemaDto,
    @SerializedName("screenshots") val capturasDePantalla: List<CapturaDePantallaDto>
)


