package com.example.infraestructura.compartido.inyeccionDeDependencias

import com.example.infraestructura.compartido.clienteHttp.ServicioDeJuego
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuloClienteHttp {
    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider(): String = "https://www.freetogame.com/api/"

    @Provides
    @Singleton
    fun proveedorDeGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun proveedorDeClienteHttp(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun proveedorDeRetrofit(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        @Named("baseUrl") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun proveedorDeServicioDeJuego(retrofit: Retrofit): ServicioDeJuego =
        retrofit.create(ServicioDeJuego::class.java)
}