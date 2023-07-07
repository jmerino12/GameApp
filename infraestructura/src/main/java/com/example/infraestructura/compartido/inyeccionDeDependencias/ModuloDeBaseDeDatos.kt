package com.example.infraestructura.compartido.inyeccionDeDependencias

import android.content.Context
import androidx.room.Room
import com.example.infraestructura.compartido.persistencia.GameAppBaseDeDatos
import com.example.infraestructura.juego.persistencia.dao.DaoJuego
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuloDeBaseDeDatos {
    @Provides
    @Singleton
    fun proveedorDeBaseDeDatos(@ApplicationContext context: Context): GameAppBaseDeDatos =
        Room.databaseBuilder(
            context,
            GameAppBaseDeDatos::class.java,
            "movies-db"
        ).build()

    @Provides
    fun provedorDeDaoJuego (baseDeDatos: GameAppBaseDeDatos) : DaoJuego = baseDeDatos.daoJuego()
}