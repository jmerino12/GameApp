# GameApp
Este proyecto cuenta con Clean Architecture, la cual consta de 3 capas, dominio, infraestrcuctura y presentación.
Cada una cuenta con su propia responsabilidad.

## Información general

El proyecto consta de una aplicación que muestra un listado de juegos usando el api [FreeToGame](https://www.freetogame.com/api-doc), al dar tap en alguna de ellas muestra un detalle de la misma.

## Funcionalidades:
* Visualizar los juegos favoritos y/o desde el webservice.
* Poder filtrar los juegos según el editor o el genero.


## Arquitectura
Se propuso como arquitectura para el proyecto una arquitectura limpia como DDD enfocada en el modelo de dominio y en el apartado de aplicación se usó MVVM.

## Estructura del proyecto
Se identifican 3 módulos
* App - Como capa de presentación
* Domain - Como capa de dominio donde encontramos nuestras reglas de negocio y nuestros servicios 
* Infrastructure - Como capa mas externa donde encontramos los detalles (Base Datos, Cliente Htt, etc)

## Construido con

### [Retrofit](https://square.github.io/retrofit/) - Cliente HTTP
Como cliente HTTP para el consumo de servicios Rest se usó “Retrofit”  ya que facilita este trabajo en aplicaciones Android, reduciendo el código repetitivo usando JSON como modelo de datos
### [Room](https://developer.android.com/jetpack/androidx/releases/room) - Persistencia de datos
Para la base de datos se usó Room ya que es una biblioteca de persistencias que nos brinda una capa de abstracción para SQLite permitiendo acceder a la base de datos facilmente ademas de ser la recomendada por Android
### [Hilt](https://dagger.dev/hilt/) - Inyección de dependencias
Para realizar la inyección de dependencias se usó la librería “Hilt”  la cual proporciona una forma estándar de usar la inyección de dependencias y es recomendado oficialmente para el desarrollo de Android
### [Flow](https://developer.android.com/kotlin/flow) - Programación Asíncrona
Se implementó Flow como flujo de datos, se usa para recibir actualizaciones en vivo desde la base de datos, y desde el servicio
### [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Para administrar la interfaz de usuario
Para almacenar y administrar datos relacionados con la IU de manera optimizada para los ciclos de vida.
### [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Navegación de interfaz de usuario
El componente Navigation de Android Jetpack te permite implementar la navegación, desde simples clics de botones hasta patrones más complejos, como las barras de apps y los paneles laterales de navegación. El componente Navigation también garantiza una experiencia del usuario coherente y predecible
### [Compose](https://developer.android.com/jetpack/compose) - Interfaz de usuario
Jetpack Compose es el kit de herramientas moderno de Android para compilar IU nativas. Simplifica y acelera el desarrollo de la IU en Android.
### [JUnit](https://junit.org/junit5/) - Testing
JUnit como Framework para la automatización de las pruebas (tanto unitarias, como de integración) en los proyectos Software.
### [Mockito](https://site.mockito.org/) - Crear Mocks de objetos en Testing
Mockito es un marco de prueba de código abierto para Java. El marco permite la creación de objetos dobles de prueba en pruebas unitarias automatizadas con el propósito de desarrollo basado en pruebas o desarrollo basado en comportamiento.
### [Coil](https://coil-kt.github.io/coil/getting_started/) - Framework para carga de imágenes
Una biblioteca de carga de imágenes para Android respaldada por Kotlin Coroutines, Coil realiza una serie de optimizaciones, incluido el almacenamiento en caché de memoria y disco, la reducción de la muestra de la imagen en la memoria, la reutilización de mapas de bits, la pausa/cancelación automática de solicitudes y más.

## Version

**Version name :** 1.0
 

