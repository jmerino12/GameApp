package com.example.dominio.juego.modelo

import com.example.dominio.juego.excepciones.ExcepcionDeParametroVacio
import org.junit.Assert
import org.junit.Test

class PruebaDeJuego {
    @Test
    fun crearJuegoConDatoValidos_creacionDeInstanciaDeJuego() {

        //ARRANGE
        val tituloDelJuego = "Call of duty"
        val generoDelJuego = "Disparos"
        val descripcionDelJuego =
            "Un Battle Royale independiente gratuito y modos accesibles a través de Call of Duty: Modern Warfare."
        val miniaturaDelJuego = "/thumbnail.jpg"
        val plataformaDelJuego = "XBOX"
        val editorDelJuego = "Activision"
        val fechaDeLanzamientoDelJuego = "2020-03-10"

        // ACT
        val juego = Juego(
            titulo = tituloDelJuego,
            genero = generoDelJuego,
            descripcion = descripcionDelJuego,
            miniatura = miniaturaDelJuego,
            plataforma = plataformaDelJuego,
            editor = editorDelJuego,
            fechaDeLanzamiento = fechaDeLanzamientoDelJuego
        )

        //ASSERT
        Assert.assertNotNull(juego)
    }

    @Test
    fun crearJuegoConElTituloVacio_lanzarExcepcionDeParametroVacio() {

        //ARRANGE
        val tituloDelJuego = ""
        val generoDelJuego = "Disparos"
        val descripcionDelJuego =
            "Un Battle Royale independiente gratuito y modos accesibles a través de Call of Duty: Modern Warfare."
        val miniaturaDelJuego = "/thumbnail.jpg"
        val plataformaDelJuego = "XBOX"
        val editorDelJuego = "Activision"
        val fechaDeLanzamientoDelJuego = "2020-03-10"

        // ACT - ASSERT
        Assert.assertThrows(ExcepcionDeParametroVacio::class.java) {
            Juego(
                titulo = tituloDelJuego,
                genero = generoDelJuego,
                descripcion = descripcionDelJuego,
                miniatura = miniaturaDelJuego,
                plataforma = plataformaDelJuego,
                editor = editorDelJuego,
                fechaDeLanzamiento = fechaDeLanzamientoDelJuego
            )
        }
    }

    @Test
    fun crearJuegoConElGeneroVacio_lanzarExcepcionDeParametroVacio() {

        //ARRANGE
        val tituloDelJuego = "Call of duty"
        val generoDelJuego = ""
        val descripcionDelJuego =
            "Un Battle Royale independiente gratuito y modos accesibles a través de Call of Duty: Modern Warfare."
        val miniaturaDelJuego = "/thumbnail.jpg"
        val plataformaDelJuego = "XBOX"
        val editorDelJuego = "Activision"
        val fechaDeLanzamientoDelJuego = "2020-03-10"

        // ACT - ASSERT
        Assert.assertThrows(ExcepcionDeParametroVacio::class.java) {
            Juego(
                titulo = tituloDelJuego,
                genero = generoDelJuego,
                descripcion = descripcionDelJuego,
                miniatura = miniaturaDelJuego,
                plataforma = plataformaDelJuego,
                editor = editorDelJuego,
                fechaDeLanzamiento = fechaDeLanzamientoDelJuego
            )
        }

    }

    @Test
    fun crearJuegoConLaDescripcionVacio_lanzarExcepcionDeParametroVacio() {

        //ARRANGE
        val tituloDelJuego = "Call of duty"
        val generoDelJuego = "Disparos"
        val descripcionDelJuego = ""
        val miniaturaDelJuego = "/thumbnail.jpg"
        val plataformaDelJuego = "XBOX"
        val editorDelJuego = "Activision"
        val fechaDeLanzamientoDelJuego = "2020-03-10"

        // ACT - ASSERT
        // ACT - ASSERT
        Assert.assertThrows(ExcepcionDeParametroVacio::class.java) {
            Juego(
                titulo = tituloDelJuego,
                genero = generoDelJuego,
                descripcion = descripcionDelJuego,
                miniatura = miniaturaDelJuego,
                plataforma = plataformaDelJuego,
                editor = editorDelJuego,
                fechaDeLanzamiento = fechaDeLanzamientoDelJuego
            )
        }
    }

    @Test
    fun crearJuegoConLaMiniaturaVacia_lanzarExcepcionDeParametroVacio() {

        //ARRANGE
        val tituloDelJuego = "Call of duty"
        val generoDelJuego = "Disparos"
        val descripcionDelJuego =
            "Un Battle Royale independiente gratuito y modos accesibles a través de Call of Duty: Modern Warfare."
        val miniaturaDelJuego = ""
        val plataformaDelJuego = "XBOX"
        val editorDelJuego = "Activision"
        val fechaDeLanzamientoDelJuego = "2020-03-10"

        // ACT - ASSERT
        // ACT - ASSERT
        Assert.assertThrows(ExcepcionDeParametroVacio::class.java) {
            Juego(
                titulo = tituloDelJuego,
                genero = generoDelJuego,
                descripcion = descripcionDelJuego,
                miniatura = miniaturaDelJuego,
                plataforma = plataformaDelJuego,
                editor = editorDelJuego,
                fechaDeLanzamiento = fechaDeLanzamientoDelJuego
            )
        }
    }

    @Test
    fun crearJuegoConLaPlataformaVacia_lanzarExcepcionDeParametroVacio() {

        //ARRANGE
        val tituloDelJuego = "Call of duty"
        val generoDelJuego = "Disparos"
        val descripcionDelJuego =
            "Un Battle Royale independiente gratuito y modos accesibles a través de Call of Duty: Modern Warfare."
        val miniaturaDelJuego = "/thumbnail.jpg"
        val plataformaDelJuego = ""
        val editorDelJuego = "Activision"
        val fechaDeLanzamientoDelJuego = "2020-03-10"

        // ACT - ASSERT
        // ACT - ASSERT
        Assert.assertThrows(ExcepcionDeParametroVacio::class.java) {
            Juego(
                titulo = tituloDelJuego,
                genero = generoDelJuego,
                descripcion = descripcionDelJuego,
                miniatura = miniaturaDelJuego,
                plataforma = plataformaDelJuego,
                editor = editorDelJuego,
                fechaDeLanzamiento = fechaDeLanzamientoDelJuego
            )
        }
    }

    @Test
    fun crearJuegoConElEditorVacia_lanzarExcepcionDeParametroVacio() {

        //ARRANGE
        val tituloDelJuego = "Call of duty"
        val generoDelJuego = "Disparos"
        val descripcionDelJuego =
            "Un Battle Royale independiente gratuito y modos accesibles a través de Call of Duty: Modern Warfare."
        val miniaturaDelJuego = "/thumbnail.jpg"
        val plataformaDelJuego = "XBOX"
        val editorDelJuego = ""
        val fechaDeLanzamientoDelJuego = "2020-03-10"

        // ACT - ASSERT
        // ACT - ASSERT
        Assert.assertThrows(ExcepcionDeParametroVacio::class.java) {
            Juego(
                titulo = tituloDelJuego,
                genero = generoDelJuego,
                descripcion = descripcionDelJuego,
                miniatura = miniaturaDelJuego,
                plataforma = plataformaDelJuego,
                editor = editorDelJuego,
                fechaDeLanzamiento = fechaDeLanzamientoDelJuego
            )
        }
    }

    @Test
    fun crearJuegoConLaFechaDeLanzamientoVacia_lanzarExcepcionDeParametroVacio() {

        //ARRANGE
        val tituloDelJuego = "Call of duty"
        val generoDelJuego = "Disparos"
        val descripcionDelJuego =
            "Un Battle Royale independiente gratuito y modos accesibles a través de Call of Duty: Modern Warfare."
        val miniaturaDelJuego = "/thumbnail.jpg"
        val plataformaDelJuego = "XBOX"
        val editorDelJuego = "Activision"
        val fechaDeLanzamientoDelJuego = ""

        // ACT - ASSERT
        // ACT - ASSERT
        Assert.assertThrows(ExcepcionDeParametroVacio::class.java) {
            Juego(
                titulo = tituloDelJuego,
                genero = generoDelJuego,
                descripcion = descripcionDelJuego,
                miniatura = miniaturaDelJuego,
                plataforma = plataformaDelJuego,
                editor = editorDelJuego,
                fechaDeLanzamiento = fechaDeLanzamientoDelJuego
            )
        }
    }
}