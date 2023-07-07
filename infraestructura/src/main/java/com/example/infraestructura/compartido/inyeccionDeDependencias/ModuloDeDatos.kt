package com.example.infraestructura.compartido.inyeccionDeDependencias

import com.example.dominio.juego.repositorios.RepositorioDeJuegos
import com.example.infraestructura.compartido.clienteHttp.ServicioDeJuego
import com.example.infraestructura.juego.persistencia.dao.DaoJuego
import com.example.infraestructura.juego.repositorios.ProxyDeJuego
import com.example.infraestructura.juego.repositorios.RepositorioDeJuegosRetrofit
import com.example.infraestructura.juego.repositorios.RepositorioDeJuegosRoom
import com.example.infraestructura.juego.repositorios.contratos.RepositorioLocalDeJuegos
import com.example.infraestructura.juego.repositorios.contratos.RepositorioRemotoDeJuegos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ModuloDeDatos {
    @Provides
    fun proveedorDeRepositorios(
        repositorioLocalDeJuegos: RepositorioLocalDeJuegos,
        repositorioRemotoDeJuegos: RepositorioRemotoDeJuegos,
    ): RepositorioDeJuegos =
        ProxyDeJuego(repositorioLocalDeJuegos, repositorioRemotoDeJuegos)

    @Provides
    fun proveedorDeFuenteLocal(daoJuego: DaoJuego): RepositorioLocalDeJuegos =
        RepositorioDeJuegosRoom(daoJuego)

    @Provides
    fun proveedorDeFuenteRemota(
        servicioDeJuego: ServicioDeJuego
    ): RepositorioRemotoDeJuegos =
        RepositorioDeJuegosRetrofit(servicioDeJuego)
}