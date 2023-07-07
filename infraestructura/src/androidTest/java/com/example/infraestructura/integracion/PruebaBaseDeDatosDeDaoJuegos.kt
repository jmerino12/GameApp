package com.example.infraestructura.integracion

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.infraestructura.compartido.DaoJuegosTestBase
import com.example.infraestructura.compartido.ReglaDeCorrutinas
import com.example.infraestructura.juego.persistencia.dao.DaoJuego
import com.example.infraestructura.juego.persistencia.entidad.JuegoEntidad
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PruebaBaseDeDatosDeDaoJuegos : DaoJuegosTestBase() {

    @get:Rule
    private val testDispatcher = ReglaDeCorrutinas()

    private lateinit var daoJuego: DaoJuego


    @Before
    override fun crearBaseDeDatos() {
        super.crearBaseDeDatos()
        daoJuego = gameAppBaseDeDatos.daoJuego()
    }

    @After
    override fun cerrarBaseDeDatos() {
        super.cerrarBaseDeDatos()
    }

    @Test
    fun obtenerJuegos_guardarUnJuegoPosteriomenteDevolverTodosLosJuegosDesdeLaBaseDeDatos_exito() =
        runBlocking {
            //ARRANGE
            val entidadDeJuego = JuegoEntidad(
                12,
                "Call of duty",
                "Disparos",
                "Un Battle Royale independiente gratuito y modos accesibles a través de Call of Duty: Modern Warfare.",
                "/thumbnail.jpg",
                "XBOX",
                "Activision",
                "2020-03-10"
            )

            daoJuego.insertarJuego(entidadDeJuego)
            //ACT
            val result = daoJuego.obtenerJuegos().first()

            Assert.assertEquals(1, result.size)
        }

    @Test
    fun obtenerJuegoPorIdentificador_guardarUnJuegoPosteriomenteDevolverElJuegoDesdeLaBaseDeDatos_exito() =
        runBlocking {
            //ARRANGE
            val entidadDeJuego = JuegoEntidad(
                12,
                "Call of duty",
                "Disparos",
                "Un Battle Royale independiente gratuito y modos accesibles a través de Call of Duty: Modern Warfare.",
                "/thumbnail.jpg",
                "XBOX",
                "Activision",
                "2020-03-10"
            )

            daoJuego.insertarJuego(entidadDeJuego)
            //ACT
            val result = daoJuego.obtenerJuegoPorIdentificador(entidadDeJuego.identificador).first()

            Assert.assertEquals(entidadDeJuego, result)
        }


}