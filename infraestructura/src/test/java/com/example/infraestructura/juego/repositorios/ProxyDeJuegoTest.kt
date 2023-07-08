package com.example.infraestructura.juego.repositorios

import com.example.dominio.juego.modelo.Juego
import com.example.dominio.juego.modelo.JuegoBase
import com.example.dominio.juego.modelo.JuegoDetalle
import com.example.dominio.juego.modelo.RequisitosMinimosDelSistema
import com.example.infraestructura.compartido.VerificadorDeInternet
import com.example.infraestructura.compartido.clienteHttp.excepciones.ExcepcionDeInternet
import com.example.infraestructura.juego.repositorios.contratos.RepositorioLocalDeJuegos
import com.example.infraestructura.juego.repositorios.contratos.RepositorioRemotoDeJuegos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProxyDeJuegoTest {
    @Mock
    private lateinit var repositorioLocal: RepositorioLocalDeJuegos

    @Mock
    private lateinit var repositorioRemotoDeJuegos: RepositorioRemotoDeJuegos

    @Mock
    private lateinit var verificadorDeInternet: VerificadorDeInternet

    @InjectMocks
    private lateinit var proxyDeJuego: ProxyDeJuego


    private val flowJuegosDesdeLocal: Flow<List<Juego>> =
        flow {
            emit(
                listOf(
                    Juego(
                        10,
                        "Overwatch 2",
                        "Disparos",
                        "A hero-focused first-person team shooter from Blizzard Entertainment.\",",
                        "/thumbnail.jpg",
                        "PC",
                        "Activision Blizzard",
                        "2022-10-04",
                        favorito = false
                    )
                )
            )
        }
    private val flowJuegosDesdeRemoto: Flow<List<Juego>> =
        flow {
            emit(
                listOf(
                    Juego(
                        11,
                        "Halo Infinite",
                        "Disparos",
                        "For the first time ever, a free-to-play Halo experience is available in the form of Halo Infinite’s multiplayer.",
                        "/thumnail.jpg",
                        "XBOX",
                        "Xbox Game Studios",
                        "2021-11-15",
                        favorito = true
                    )
                )
            )
        }

    private val flowJuegoDesdeRemoto: Flow<JuegoDetalle> =
        flow {
            emit(

                JuegoDetalle(
                    identificador = 11,
                    titulo = "Halo Infinite",
                    genero = "Disparos",
                    descripcionCorta = "For the first time ever, a free-to-play Halo experience is available in the form of Halo Infinite’s multiplayer.",
                    descripcion = "",
                    miniatura = "/thumnail.jpg",
                    plataforma = "XBOX",
                    editor = "Xbox Game Studios",
                    fechaDeLanzamiento = "2021-11-15",
                    requisitosMinimosDelSistema = RequisitosMinimosDelSistema("", "", "", "", ""),
                    capturaDePantalla = listOf(),
                    urlJuego = "",
                    favorito = true
                )

            )
        }


    @Test
    fun obtenerJuegos_cuandoHayInternet_listadoDeJuegoDelLocalyRemoto() = runTest {

        //ARRANGE
        Mockito.`when`(verificadorDeInternet.hayConexionInternet()).thenReturn(true)
        Mockito.`when`(repositorioLocal.obtenerJuegos()).thenReturn(flowJuegosDesdeLocal)
        Mockito.`when`(repositorioRemotoDeJuegos.obtenerJuegos()).thenReturn(flowJuegosDesdeRemoto)

        //ACT
        proxyDeJuego.obtenerJuegos().collect { resultado ->
            //ASSERT
            Assert.assertEquals(resultado.size, 2)
        }

        Mockito.verify(repositorioLocal, Mockito.times(1)).obtenerJuegos()
        Mockito.verify(repositorioRemotoDeJuegos, Mockito.times(1)).obtenerJuegos()
        Mockito.verify(verificadorDeInternet, Mockito.times(1)).hayConexionInternet()

    }

    @Test
    fun obtenerJuegos_cuandoNoHayInternet_listadoDeJuegoDelLocal() = runTest {

        //ARRANGE
        Mockito.`when`(verificadorDeInternet.hayConexionInternet()).thenReturn(false)
        Mockito.`when`(repositorioLocal.obtenerJuegos()).thenReturn(flowJuegosDesdeLocal)

        //ACT
        proxyDeJuego.obtenerJuegos().collect { resultado ->
            //ASSERT
            Assert.assertEquals(resultado.size, 1)
        }

        Mockito.verify(repositorioLocal, Mockito.times(1)).obtenerJuegos()
        Mockito.verify(repositorioRemotoDeJuegos, Mockito.times(0)).obtenerJuegos()
        Mockito.verify(verificadorDeInternet, Mockito.times(1)).hayConexionInternet()

    }

    @Test
    fun obtenerJuego_cuandoHayInternet_juegoDesdeRemoto() = runTest {

        //ARRANGE
        Mockito.`when`(verificadorDeInternet.hayConexionInternet()).thenReturn(true)
        Mockito.`when`(repositorioRemotoDeJuegos.obtenerJuego(identificador = 11))
            .thenReturn(flowJuegoDesdeRemoto)

        //ACT
        proxyDeJuego.obtenerJuego(11).collect { resultado ->
            //ASSERT
            Assert.assertNotNull(resultado)
        }

        Mockito.verify(repositorioRemotoDeJuegos, Mockito.times(0)).obtenerJuegos()
        Mockito.verify(verificadorDeInternet, Mockito.times(1)).hayConexionInternet()
        Mockito.verify(repositorioRemotoDeJuegos, Mockito.times(1)).obtenerJuego(11)
    }

    @Test(expected = ExcepcionDeInternet::class)
    fun obtenerJuego_cuandoNoHayInternet_lanzamientoDeExcepcionDeInternet() = runTest {

        //ARRANGE
        Mockito.`when`(verificadorDeInternet.hayConexionInternet()).thenReturn(false)

        //ACT
        proxyDeJuego.obtenerJuego(11)

        //ASSERT
        Mockito.verify(repositorioRemotoDeJuegos, Mockito.times(0)).obtenerJuegos()
        Mockito.verify(verificadorDeInternet, Mockito.times(2)).hayConexionInternet()
        Mockito.verify(repositorioRemotoDeJuegos, Mockito.times(0)).obtenerJuego(11)
    }

}