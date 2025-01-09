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


## Próximos pasos 📋

- Definir e introducir todos los apartados principales de la aplicación.
- Diseñar las pantallas de cada una de estas secciones.
- Diseñar una pantalla para el perfil del usuario.
