# Wrestling App


## Descripción

Una aplicación de ejercicio enfocada al progreso tanto físico como técnico en el deporte de la Lucha Olímpica.
Esta aplicación incluirá tutoriales paso a paso enfocados en la progresión de las técnicas. Rutinas físicas personalizables con ejercicios enfocados al desarrollo físico para la mejora en el deporte.
Incluirá apartados de logros y de registros personales para poder monitorizar el progreso adecuadamente.
Adicionalmente podrá contar con un apartado para el seguimiento de torneos o la visualización de aquellos que se emitan en abierto (acceso a internet).


## Estado

⚠️ Estado del Proyecto: En Desarrollo ⚠️

Este proyecto se encuentra en una fase inicial, aún queda un largo camino por delante. En este momento solo cuenta con una pantalla principal donde se disponen fotos y descripciones de algunas de las principales secciones y un título. Existe una pequeña navegación básica: las imágenes son elementos clickables que redirigen a otra pantalla aún en construcción


## Objetivos

El objetivo es crear una aplicación que permita seguir una progresión y monitorizarla para desarrollar tus habilidades en el campo de la lucha Olímpica, esto incluirá desde progresiones en ejercicios orientados al desarrollo físico (flexibilidad, fuerza...), como a la técnica.

En el futuro, la aplicación incluirá una base de datos que permita:

- Almacenar y monitorizar el progreso del usuario: Registros de entrenamiento, habilidades técnicas trabajadas, progresión de la fuerza, flexibilidad...
- Almacenar perfiles de usuario personalizados: Cada usuario tendrá su propio perfil, donde podrán ver su historial de entrenamiento y logros.


## Cambios v2.0

En esta versión se añaden:

1. Logs encargados de aportar información de las fases del ciclo de vida del Activity (apertura de la app, minimización, rotación...).

2. Dependencias de Room para empezar a trabajar con bases de Datos. Adicionalmente se crea una "Entity" y un "Dao" como introducción al uso de Room (El código relacionado se encuentra en el archivo RoomDb.kt, ubicado en la carpeta data).

3. Se añade un botón Salir que ejecuta "finishAndRemoveTask()" con el fin de comprobar que se muestra correctamente el Log definido en la función "onDestroy"

4. Modificaciones en el AndroidManifest para personalizar la app: 

  - El nombre de la aplicación se asigna desde el archivo res/values/strings.xml utilizando la clave "app_name". Para ello, en el AndroidManifest se establece la etiqueta "android:label="@string/app_name"").
  - Se crea una clase que hereda de "application" para manejar funciones globales, como "onLowMemory".
  - Se fija la orientación del Activity en vertical.
  - Se declaran permisos de internet y almacenamiento.
  - Se añade al Activity: "android:configChanges="orientation|screenLayout"" para poder manejar los cambios de rotación desde el Activity con la función "onConfigurationChanged".


## Cambios v3.0

En esta versión se añaden:

1. Una base de datos con "SqLite" dedicada a los ejercicios, los usuarios, las categorias de los ejercicios y el progreso de los usuarios:

    - Relaciones:
      - Los ejercicios pertenecen a una categoría 
      - El progreso relaciona a los usuarios con los ejercicios en una fecha determinada
      
    - Contrato: 
      - Se crea una clase contrato (DbContract.kt) donde se definen las tablas

    - Helper:
      - Se crea una clase helper (DbHelper.kt) donde se define el comportamiento de la base de datos cuando se crea "onCreate" y cuando se actualiza "onUpgrade". 
        Además de definirse la versión y el nombre de la base de datos en el "companion object".
      - En el método "onCreate" de la clase "DbHelper" se introducen los datos iniciales de la base de datos, para que en cuanto se cree ya tenga contenido.

    - Repository:
      - Cada una de las tablas tiene su propia clase Repository donde se define el CRUD; es decir, los métodos necesarios para el "Create", "Update", "Read" y "Delete"
        de los datos de la tabla.
      - De momento los Repositorios cuentan con consultas genéricas para operaciones tales como mostrar todos los usuarios (getAllUsers()); a medida que la aplicación
        necesite realizar otro tipo de consultas, se añadiran las funciones necesarias para ello en el Repository de la tabla.

   - WrestlingApplication:
     - Se crea la variable "lateinit var dbHelper: WrestlingSqliteHelper". Esta variable se inicializa en el método onCreate de la clase Application 
       "dbHelper = WrestlingSqliteHelper(this)" para crear una única instancia de la base de datos de SqLite que compartan todas las clases Repository; 
       de este modo, cuando se utilizan las funciones de los repositorios no se tiene que instanciar varias veces la misma base de datos: 
       "private val dbHelper = (context.applicationContext as WrestlingApplication).dbHelper"; en cada uno de las funciones se utiliza esta instancia: 
       "val db = dbHelper.writableDatabase".

   - MainActivity:
     - Se han modificado cada una de las pantallas de los apartados de tecnicas ("PantallaTecnica") para aceptar una lista con los ejercicios que ocupan esa categoría y 
       mostrarla por pantalla.
     - Esta lista se consigue con una consulta a la base de datos de Sqlite; se extraen todos los ejercicios de una categoría con la función "getExercisesByCategory()" 
       del repositorio "ExerciseRepository". Para realizar esta consulta contra la base de datos se utiliza desde el propio NavHost una consulta a través de un 
       "LaunchedEffect(key){withContext(Dispatchers.IO) {}}". De este modo se lanza la corutina con el "LaunchedEffect" y se pasa a segundo plano con el "withContext
       (Dispatchers.IO)".

   - Anotación 1: Está comentado en el código un botón que aumenta un contador además de "delay(5000)" en el LaunchedEffect para comprobar que la interfaz gráfica no se
     congela durante el trabajo que se realiza al acceder a la base de datos

   - Anotación 2: Siendo consciente de que no es muy buena práctica hacer el acceso a la base de datos desde el NavHost pero estoy esperando a ver el ViewModel y a 
     entenderlo bien antes de ponerme a dividir el código en clases. Podría haber evitado realizar el acceso desde el NavHost y hacerlo desde el composable 
     "PantallaTecnica"; pero seguiría siendo una mala práctica y este modelo ya se utiliza para acceder a la otra base de datos (room). Así que lo situé en el NavHost 
     a modo de aprendizaje para comprobar si funcionaba, pero esta ubicación es temporal.

2. Una base de datos con "Room" dedicada a los torneos, los pesos y las categorias:

    - Relaciones:
        - .
        - .

    - Entity:
        - .

    - Dao:
        - .


## Próximos pasos 📋

- Definir e introducir todos los apartados principales de la aplicación.
- Diseñar las pantallas de cada una de estas secciones.
- Diseñar una pantalla para el perfil del usuario.
