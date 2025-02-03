package com.carlosgarciaalonso.wrestlingapp.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carlosgarciaalonso.wrestlingapp.ui.theme.WrestlingAppTheme
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.carlosgarciaalonso.wrestlingapp.R
import com.carlosgarciaalonso.wrestlingapp.WrestlingApplication
import com.carlosgarciaalonso.wrestlingapp.data.network.ChuckNorrisService
import com.carlosgarciaalonso.wrestlingapp.data.network.RandomJokeReponse
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.AppDatabase
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.RoomCallback
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.combinados.TournamentWithCategories
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.Tournament
import com.carlosgarciaalonso.wrestlingapp.data.sqlitedb.repositories.ExerciseRepository
import com.carlosgarciaalonso.wrestlingapp.ui.viewmodels.TorneoViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

// Clase para los grupos de imagenes y técnicas
// La diferencia entre una clase normal y una "data class" es que la "data class" no necesita declarar
// explícitamente funciones como "ToString"; este tipo de clases son más cómodas para trabajar con datos
// estructurados, tienen funcionalidades predefinidas para tratar datos
// Esta clase se crea para crear objetos "Tecnica" que luego se añadirán a la lista de técnicas
data class Tecnica(val imageRes: Int, val title: String)

// Esta clase es el punto de entrada de la app (algo así como el "Main" del Compose)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val TAG = this.javaClass.simpleName

    //Para importar directamente esto hay que usar el comando "Alt" + "Ins" -> Darle a Override Methods
    //-> Escribir el nombre de la funcion (Por ejemplo "onStart()". Y ya se importa de forma automática)
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "La aplicación se puede ver (onStart)")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Aplicación reanudada (onResume)")


    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Aplicación minimizada (onPause)")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "La aplicación ya no es visible (onStop)")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "Aplicación restaurada (onRestart))")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Aplicación cerrada (onDestroy)")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "Guardado del estado (onSaveInstanceState)")
    }

    // Para majear los cambios de configuración añadí al Manifest la línea
    //"android:configChanges="orientation|screenLayout"". Tuve que añadir el"screenLayout" porque si
    // ponía "orientation|screenSize", al pasar de vertical a horizontal; la aplicación destruía su
    // actividad y volvía a recomponerse. Por el contrario, al pasar de
    // horizontal a vertical si que se mostraba correctamente el log del "onConfigurationChanged".
    // Gracias a poner el "screenLayout" ya se muestra el log correctamente en ambas ocasiones aunque
    // ahora hay que manejar la posibilidad de que se muestre el log con el mensaje de que la pantalla
    // ha sido rotada en otras ocasiones como en cambios de resolución del movil.
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Verifica si realmente ha habido un cambio de orientación
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ||
            newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Log que se muestra cuando se rota la pantalla:
            Log.d(TAG, "El Activity ha rotado: orientación actual " +
                    if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        "vertical"
                    } else {
                        "horizontal"
                    }
            )
        } else {
            Log.d(TAG, "Otro cambio de screenLayout")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Se guarda la base de datos que se ha instanciado en la clase WrestlingApplication
        Log.d(TAG, "Se ha abierto la aplicación (onCreate)")
        enableEdgeToEdge()
        // Esta función se utiliza para definir el contenido en pantalla:
        setContent {
            // Y esta segunda función se utiliza para aplicar un tema de forma global a todos los
            // contenidos:
            WrestlingAppTheme {
                // No se puede instanciar directamente una lista con "List<Tecnica>" porque en Kotlin
                // "List" no es un constructor; hay que utilizar funciones como "listOf()" (listOf()
                // genera una lista inmutable)
                val tecnicasList = listOf<Tecnica>(
                    Tecnica(imageRes = R.drawable.tecnicas_basicas, title = "Técnicas Básicas"),
                    Tecnica(imageRes = R.drawable.tecnicas_avanzadas, title = "Técnicas Avanzadas"),
                    Tecnica(imageRes = R.drawable.gran_amplitud, title = "Técnicas de Gran Amplitud"),
                    Tecnica(imageRes = R.drawable.tecnicas_suelo, title = "Técnicas de Suelo"),
                    Tecnica(imageRes = R.drawable.fisica_lucha, title = "Física")
                )
                // Compose funciona de modo que cada cambio en la interfaz provoca que se tenga que
                // "redibujar". "rememberNavController" permite que cuando ocurra este redibujado
                // de la interfaz, la aplicación recuerde el punto en el que se encontraba. Si no podría
                // ocurrir que el usuario volviese a la pantalla principal y perdiese lo que estaba viendo
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, tecnicasList = tecnicasList)
            }
        }
    }
}

// Básicamente esta función crea un gráfico de navegación para moverse entre las distintas pantallas
// de la aplicación
@Composable //Utilizamos Composable para decir que estamos definiendo una UI
// "navController" es el controlador que indica como se viaja de una pantalla a otra
// "tecnicasList" es la lista de técnicas, se pasa como parámetro para poder tener acceso a esos datos
// desde las distintas pantallas
fun SetupNavGraph(navController: NavHostController, tecnicasList: List<Tecnica>) {
    NavHost(
        //Se asigna el controlador de navegación que se pasa como parámetro
        navController = navController,
        // Es la pantalla de inicio de la aplicación cuando se lanza
        startDestination = "pantalla_principal"
    ) { // Dentro del NavHost se definen las diferentes pantallas (composables de la aplicación)
        // "composable" es la función que define cada pantalla individual
        // La "route" es como el identificador de la pantalla
        composable(route = "pantalla_principal") {
            // Se pasan a la función "MainScreen" la lista de técnicas para que las muestre y se le
            // dice que cuando la función onclick adquiera valor, se ejecute "navController.navigate
            // con la ruta de destino correcta
            MainScreen(tecnicas = tecnicasList, onclick = { route ->
                navController.navigate(route)})
        }

        // Pantalla de detalles de cada técnica
        // Al poner "\{tituloTecnica}" se utiliza una ruta dinámica, cuando se navega a esta pantalla
        // se asigna un valor a "tituloTecnica" en función de la página que se quiere mostrar
        composable(route = "detalle/{tituloTecnica}") { backStackEntry ->    // backStackEntry
            // representa la página que se está mostrando en la aplicación, básicameente aquí se
            // guarda toda la información de la pantalla actual de la aplicación; eso incluye los
            // argumentos que se pasaron. Por lo tanto "backStackEntry.arguments" incluye los
            // argumentos que se pasaron a esta pantalla. El modificador "?" se pone para que no
            // "pete" aunque sea null.

            //Guarda el contexto del Activity
            val context = LocalContext.current
            //Con el "remember" nos aseguramos de que solo se inicialice una vez
            val exerciseRepository = remember { ExerciseRepository(context) }
            // En la variable "exercises" se va a guardar la lista con los ejercicios que corresponden
            // a cada apartado
            val exercises = rememberSaveable { mutableStateOf<List<String>>(emptyList()) }

            // En la variable "apartadoTecnica" se almacena el valor asociado a la clave "tituloTecnica"
            // (definido en la ruta); por lo tanto, si se hace "click" en la primera imagen el par
            // clave-valor sería algo así: "tituloTecnica: Técnicas Básicas".
            val apartadoTecnica = backStackEntry.arguments?.getString("tituloTecnica")

            //Usar LaunchedEffect para manejar la corutina (básicamente se encarga de crearla)
            LaunchedEffect(apartadoTecnica) {
                // El "withContext" es el encargado de cambiar el contexto de la corutina creada para
                // que el codigo que contiene se ejecute en un hilo en segundo plano
                // Podría definirse directamente la función "getExercisesByCategory" como una función
                // suspendida y aportar en la propia función el contexto
                withContext(Dispatchers.IO) {
                    //delay(5000)   //Para asegurarnos de que se ejecuta en segundo plano
                    //Acceso a la base de datos
                    if (apartadoTecnica != null && apartadoTecnica != "Física") {
                        //Se extrae la lista de ejercicios de la base de datos
                        val result = exerciseRepository.getExercisesByCategory(apartadoTecnica)
                        //Se guarda en la lista
                        exercises.value = result
                    } else if (apartadoTecnica == "Física") {
                        val result = exerciseRepository.getExercisesForFisica()
                        exercises.value = result
                    }
                }
            }

            // Por último se pasa el string con el apartado y la lista de ejercicios a la función
            // composable encargada de mostrar la información por pantalla
            PantallaTecnica(tecnicaTitle = apartadoTecnica, exercises = exercises.value)
        }
        //Pantalla para los torneos
        composable(route = "torneos") {

            PantallaTorneo()

        }
        //Pantalla para los consejos
        composable(route = "chucknorris"){
            PantallaChuckNorris()
        }
    }
}

// Sirve para poder utilizar "Material Experimental", en este caso es el "TopAppBar"
@OptIn(ExperimentalMaterial3Api::class)
@Composable // Utilizamos Composable para decir que estamos definiendo una UI
// Declaramos una función que será la pantalla principal ("Main") de la aplicación
fun MainScreen(tecnicas: List<Tecnica>, onclick: (String) -> Unit) {

    //Configuramos el comportamiento de la TopAppBar:
    //"enterAlwaysScrollBehavior" hace que la barra se esconda cuando el usuario desplaza hacia
    // abajo y que se enseñe cuando se desplaza hacia arriba
    //"rememberTopAppBarState()" recuerda el estado en el que se encuentra la "TopAppBar"
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Se crea un Scaffold que básicamente es un panel invisible que sirve para colocar los elementos
    // de una forma ordenada
    Scaffold(
        //Establecemos el color de fondo del Scaffold
        containerColor = MaterialTheme.colorScheme.background,
        // Añadimos un modificador para que el Scaffold ocupe toda la pantalla
        modifier = Modifier.fillMaxSize()
            // Cuando hay más elementos desplazables dentro de la pantalla es necesario informar al
            // "scrollBehavior" que está sucediendo un desplazamiento
            // ("scrollBehavior.nestedScrollConnection")
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {  // Le añadimos un "topBar"
            CenterAlignedTopAppBar( // Centramos el contenido del "topBar"
                //Establecemos los colores de la TopBar:
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                ),
                navigationIcon = {
                    // Botón de menú con la acción personalizada
                    IconButton(onClick = { /*onclick("Perfil")*/ }) {
                        Icon(
                            Icons.Filled.AccountCircle, // Cambia esto si quieres otro icono
                            contentDescription = "Perfil"
                        )
                    }
                },
                title = {   // Creamos un título en el "topBar"
                    Text(
                        text = "Wrestling", // texto del título
                        fontWeight = FontWeight.Bold,   // fuente en negrita
                        fontSize = 38.sp,    // Tamaño de la fuente
                        maxLines = 1,   //Máximo de líneas
                        // Cuando el texto no entra en el espacio asignado se muestra "..." en lugar
                        // de recortarlo:
                        overflow = TextOverflow.Ellipsis
                    )
                },
                // Vinculamos el "scrollBehavior" previamente configurado al "CenterAlignedTopAppBar"
                // El ".nestedScroll" detecta el movimiento y el "scrollBehavior" decide qué hacer
                // cuando se detecta ese movimiento
                scrollBehavior = scrollBehavior
            )
        },
        // Ahora se coloca el contenido del Scaffold:
        content = { autoPadding ->  // El "autoPadding" es como le llamo yo al padding que genera
            // automáticamente el propio Scaffold para los elementos que contiene
            Column(
                modifier = Modifier
                    .padding(autoPadding)   //Se añade el padding automático generado por el Scaffold
                    .fillMaxSize()  //Se ajusta para que ocupe toda la pantalla
                    // Se añade un verticalScroll para que se pueda deslizar por la pantalla cuando los
                    // elementos ocupan más del espacio visible disponible y se le pasa como argumento
                    // el "rememberScrollState()" para que cuando ocurra un "redibujado" de la interfaz,
                    // esta recuerde en que punto del "scroll" se encontraba
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally, // Se centra horizontalmente
                //"verticalArragement" controla la distribución de los elementos dentro de la "Column",
                // el modificador "Arragement.Top" indica que cuando el contenedor sea más grande que
                // lo que ocupan sus contenidos, estos se situarán en la parte de arriba y el espacio
                // restante abajo.
                verticalArrangement = Arrangement.Top
            ) { // Contenido de la columna:
                AddText(    // Utilizo una función propia para añadir un texto
                    texto = "Estas imágenes funcionarán a modo de botón para navegar por la aplicación",
                    // Añado paddings personalizados:
                    modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 32.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))   // Separación con las imágenes

                // Creo un bucle que recorre todos los objetos "Tecnica" de la lista y genera los
                // contenedores aplicando la función "ImagenTexto"
                tecnicas.forEach { tecnica ->
                    ImagenTexto(
                        imageRes = tecnica.imageRes,
                        text = tecnica.title,
                        // El contenedor se hace "clickable" para que se pueda hacer click sobre él
                        // (evidentemente)
                        modifier = Modifier.clickable {
                            // Cuando se hace click sobre él, se pasa a la funcion "onclick" el
                            // string con el nombre de la técnica para que se añada a la navController
                            onclick("detalle/${tecnica.title}")
                        }
                            .padding(start = 16.dp, end = 16.dp)
                    )
                    //Despues de cada contenedor (imagen + texto) se añade un espacio
                    Spacer(modifier = Modifier.height(16.dp))
                }

                ImagenTexto(
                    imageRes = R.drawable.torneo,
                    text = "Torneos",
                    // El contenedor se hace "clickable" para que se pueda hacer click sobre él
                    // (evidentemente)
                    modifier = Modifier.clickable {
                        // Cuando se hace click sobre él, se pasa a la funcion "onclick" el
                        // string con el nombre de la técnica para que se añada a la navController
                        onclick("torneos")
                    }
                        .padding(start = 16.dp, end = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                ImagenTexto(
                    imageRes = R.drawable.chuck_norris,
                    text = "¿Buscas Motivación?",
                    // El contenedor se hace "clickable" para que se pueda hacer click sobre él
                    // (evidentemente)
                    modifier = Modifier.clickable {
                        // Cuando se hace click sobre él, se pasa a la funcion "onclick" el
                        // string con el nombre de la técnica para que se añada a la navController
                        onclick("chucknorris")
                    }
                        .padding(start = 16.dp, end = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Este botón sirve de comprobación para el ejercicio "Agrega un log que se muestre
                // en Logcat cada vez que: "Cierras por completo la aplicación"
                BotonSalir()

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
    }


@Composable //Utilizamos Composable para decir que estamos definiendo una UI
// Esta función aún es muy primitiva pero es la que define la pantalla que se abre cuando se hace
// click en alguna de las técnicas
fun PantallaTecnica(tecnicaTitle: String?, exercises: List<String>) {

    //var count by remember { mutableStateOf(0) }

    Box(    //Se crea una caja
        modifier = Modifier
            .fillMaxSize()  //Se adapta para que cubra toda la pantalla
            .padding(16.dp),    //Se le asigna un padding general
        //Se centra el contenido al centro tanto vertical como horizontalmente
        contentAlignment = Alignment.Center
    ) { //Contenido de la caja:
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Mostrar el título de la técnica
            Text(
                text = tecnicaTitle ?: "Detalles de la técnica",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Lista de ejercicios:",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Mostrar la lista de ejercicios, si existen
            if (exercises.isNotEmpty()) {
                exercises.forEach { exercise ->
                    Text(
                        text = exercise,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            } else {
                // Mensaje si no hay ejercicios disponibles
                Text(
                    text = "No hay ejercicios disponibles.",
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

//            // Contador para comprobar que no se bloquea la interfaz grafica al acceder a la base de
//            // datos
//            Text(
//                text = "Contador: $count",
//                style = MaterialTheme.typography.headlineMedium
//            )
//            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre el texto y el botón
//
//            // Botón para aumentar el contador
//            Button(onClick = { count++ }) {
//                Text(text = "Incrementar")
//            }
        }
    }
}



@Composable
fun PantallaTorneo(
    // HiltViewModel infla el ViewModel sin que tú lo crees manualmente
    torneoViewModel: TorneoViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    // Observa el StateFlow
    val torneos by torneoViewModel.tournaments.collectAsState()

    //var count by remember { mutableStateOf(0) }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        content = { autoPadding ->
            Column(Modifier
                .fillMaxSize()
                .padding(autoPadding)
                .verticalScroll(rememberScrollState())
            ) {
                // Mostrar el título de la técnica
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Lista de torneos",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar la lista de torneos
                if (torneos.isNotEmpty()) {
                    torneos.forEach { torneo ->
                        Text(
                            text = "Torneo en ${torneo.city} el ${torneo.date} a las ${torneo.time} " +
                                    "(${torneo.categories})",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )
                    }
                } else {
                    Text(
                        text = "No hay torneos disponibles.",
                        style = MaterialTheme.typography.bodyLarge,
                        fontStyle = FontStyle.Italic
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

//            // Contador para comprobar que no se bloquea la interfaz grafica al acceder a la base de
//            // datos
//                Text(
//                    text = "Contador: $count",
//                    style = MaterialTheme.typography.headlineMedium
//                )
//                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre el texto y el botón
//
//                // Botón para aumentar el contador
//                Button(onClick = { count++ }) {
//                    Text(text = "Incrementar")
//                }

            }
        }
    )
}

@Composable
fun PantallaChuckNorris() {

    var consejo by rememberSaveable { mutableStateOf("") }

    // Creamos un scope de corrutina que se vincule al ciclo de vida del composable
    val coroutineScope = rememberCoroutineScope()

    //Esto es necesario cuando la api devuelve más claves de las que se están manejando en la clase
    val json = Json{
        ignoreUnknownKeys = true
    }
    val contentType = "application/json".toMediaType()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.chucknorris.io/")
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    val service = retrofit.create(ChuckNorrisService::class.java)

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        content = { autoPadding ->
            Column(Modifier
                .fillMaxSize()
                .padding(autoPadding)
                .verticalScroll(rememberScrollState())
            ) {
                // Mostrar el título de la técnica
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                        .padding(15.dp),
                    text = "¿Necesitas motivación?",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                        .padding(15.dp),
                    text = "No hay nada mejor que aplicar las enseñanzas de un gurú de las artes marciales:",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                        .padding(15.dp),
                    text = consejo,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        // Lanzamos otra corrutina para pedir otra broma
                        coroutineScope.launch {
                            val nuevaBroma = withContext(Dispatchers.IO) {
                                service.getRandomJoke()
                            }
                            consejo = nuevaBroma.broma
                        }
                    }
                ) {
                    Text(text = "Otro consejo")
                }

                //Como service.getRandomJoke() es una función suspendida es necesario llamarla desde una corutina:
                LaunchedEffect(Unit) {
                    withContext(Dispatchers.IO) {
                        val bromaAleatoria = service.getRandomJoke()
                        consejo = bromaAleatoria.broma
                        Log.d("Bromita", "$bromaAleatoria")
                    }

                }
            }
        }
    )
}



@Composable // Utilizamos Composable para decir que estamos definiendo una UI
// Funcion para añadir texto con unas propiedades concretas
fun AddText(texto: String, modifier: Modifier = Modifier) {
    Text(
        text = texto,   //El string que se pasa como argumento es el texto
        modifier = modifier
            .fillMaxWidth(), //Ocupa el espacio disponible
        // Con "MaterialTheme.typography.bodyMedium" ya se está añadiendo al texto un estilo
        // predefinido por Material Design. Con ".copy" lo que se hace es coger ese estilo
        // predefinido y modificar solo una de las propiedades
        style = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Justify   //Texto justificado
        )
    )
}

@Composable //Utilizamos Composable para decir que estamos definiendo una UI
//Esta es la función que genera los contenedores de imagen + texto
fun ImagenTexto(imageRes: Int, text: String, modifier: Modifier = Modifier) {
    Column( // Se crea una columna
        modifier = modifier
            .fillMaxWidth() // Se adapta al ancho disponible
            .padding(20.dp),    //Se añade un padding general
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(  //Se añade la imagen
            // Se coloca la imagen con la ruta que se pasa como argumento
            painter = painterResource(id = imageRes),
            contentDescription = text,  // Se añade el texto como descripcion de la imagen
            //Asegura que la imagen se adapte al tamaño del contenedor:
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth() // Se ajusta para que ocupe el ancho completo
                .graphicsLayer( //Bordes redondeados sin deformarse:
                    shape = RoundedCornerShape(25.dp), // Bordes redondeados
                    clip = true // Activa el recorte
                )
        )
        Text(   //Se añade el texto a continuación
            text = text,    //Se añade el texto que se pasa como argumento
            style = MaterialTheme.typography.bodyLarge, //Se pone un estilo predefinido
            modifier = Modifier
                .padding(top = 10.dp)    //Un padding superior para separarlo de la imagen
                .fillMaxWidth() //Se ajusta al ancho disponible
                .wrapContentWidth(Alignment.CenterHorizontally),    //Se centra horizontalmente
        )
    }
}

// Este botón sirve de comprobación para el ejercicio "Agrega un log que se muestre en Logcat cada
// vez que: "Cierras por completo la aplicación"
@Composable
fun BotonSalir() {
    // Información de qué se está ejecutando en cada momento; en este caso es una activity
    val context = LocalContext.current

    Button(onClick = {
        //Cierra la actividad si el contexto es una actividad
        if (context is Activity) {
            context.finishAndRemoveTask()
        }
    }) {
        Text("Salir")
    }
}
