package com.example.infraestructura.juego.clienteHttp.dto

import com.google.gson.annotations.SerializedName

data class JuegoDto(
    @SerializedName("id") var identificador: Int,
    @SerializedName("title") var titulo: String,
    @SerializedName("thumbnail") var miniatura: String,
    @SerializedName("short_description") var descripcionCorta: String,
    @SerializedName("game_url") var urlJuego: String,
    @SerializedName("genre") var genero: String,
    @SerializedName("platform") var plataforma: String,
    @SerializedName("publisher") var editor: String,
    @SerializedName("developer") var desarrollador: String,
    @SerializedName("release_date") var fechaDeLanzamiento: String,
    @SerializedName("freetogame_profile_url") var urlDelJuego: String

)