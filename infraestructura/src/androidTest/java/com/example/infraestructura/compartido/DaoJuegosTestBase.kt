package com.example.infraestructura.compartido

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.infraestructura.compartido.persistencia.GameAppBaseDeDatos
import org.junit.After
import java.io.IOException


abstract class DaoJuegosTestBase {
    protected lateinit var gameAppBaseDeDatos: GameAppBaseDeDatos

    open fun crearBaseDeDatos() {
        val contexto = ApplicationProvider.getApplicationContext<Context>()
        gameAppBaseDeDatos =
            Room.inMemoryDatabaseBuilder(contexto, GameAppBaseDeDatos::class.java).build()
    }

    @After
    @Throws(IOException::class)
    open fun cerrarBaseDeDatos() {
        gameAppBaseDeDatos.close()
    }
}