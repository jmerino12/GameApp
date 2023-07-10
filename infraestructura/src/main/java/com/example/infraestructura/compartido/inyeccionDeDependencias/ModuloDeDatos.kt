package com.example.infraestructura.compartido.inyeccionDeDependencias


import android.content.Context
import com.example.dominio.juego.excepciones.ExcepcionDeParametroVacio
import com.example.dominio.juego.repositorios.RepositorioDeJuegos
import com.example.infraestructura.R
import com.example.dominio.R as RecursosDominio
import com.example.infraestructura.compartido.VerificadorDeInternet
import com.example.infraestructura.compartido.clienteHttp.ServicioDeJuego
import com.example.infraestructura.compartido.clienteHttp.excepciones.ExcepcionDeInternet
import com.example.infraestructura.juego.persistencia.dao.DaoJuego
import com.example.infraestructura.juego.repositorios.ProxyDeJuego
import com.example.infraestructura.juego.repositorios.RepositorioDeJuegosRetrofit
import com.example.infraestructura.juego.repositorios.RepositorioDeJuegosRoom
import com.example.infraestructura.juego.repositorios.contratos.RepositorioLocalDeJuegos
import com.example.infraestructura.juego.repositorios.contratos.RepositorioRemotoDeJuegos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ModuloDeDatos {
    @Provides
    fun proveedorDeRepositorios(
        repositorioLocalDeJuegos: RepositorioLocalDeJuegos,
        repositorioRemotoDeJuegos: RepositorioRemotoDeJuegos,
        verificadorDeInternet: VerificadorDeInternet,
        excepcionDeInternet: ExcepcionDeInternet
    ): RepositorioDeJuegos =
        ProxyDeJuego(
            repositorioLocalDeJuegos,
            repositorioRemotoDeJuegos,
            verificadorDeInternet,
            excepcionDeInternet
        )

    @Provides
    fun proveedorDeFuenteLocal(daoJuego: DaoJuego): RepositorioLocalDeJuegos =
        RepositorioDeJuegosRoom(daoJuego)

    @Provides
    fun proveedorDeFuenteRemota(
        servicioDeJuego: ServicioDeJuego
    ): RepositorioRemotoDeJuegos =
        RepositorioDeJuegosRetrofit(servicioDeJuego)

    @Provides
    fun proveedorDeVerificadorDeInternet(@ApplicationContext appContext: Context): VerificadorDeInternet =
        VerificadorDeInternet(appContext)

    @Provides
    fun proveedorDeExpcecionDeInternet(@ApplicationContext appContext: Context): ExcepcionDeInternet =
        ExcepcionDeInternet(appContext.getString(R.string.mensaje_excepcion_internet))

}