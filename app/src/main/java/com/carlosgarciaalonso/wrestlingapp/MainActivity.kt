package com.carlosgarciaalonso.wrestlingapp

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carlosgarciaalonso.wrestlingapp.ui.theme.WrestlingAppTheme
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Clase para los grupos de imagenes y técnicas
// La diferencia entre una clase normal y una "data class" es que la "data class" no necesita declarar
// explícitamente funciones como "ToString"; este tipo de clases son más cómodas para trabajar con datos
// estructurados, tienen funcionalidades predefinidas para tratar datos
// Esta clase se crea para crear objetos "Tecnica" que luego se añadirán a la lista de técnicas
data class Tecnica(val imageRes: Int, val title: String)

// Esta clase es el punto de entrada de la app (algo así como el "Main" del Compose)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    Tecnica(imageRes = R.drawable.tecnicas_suelo, title = "Técnicas de Suelo")
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
            // Se pasan a la función "MainScreen" la lista de técnicas para que las muestre y el
            // "navController" para poder cambiar a otras pantallas desde la main
            MainScreen(tecnicas = tecnicasList, navController = navController)
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
            // En la variable "apartadoTecnica" se almacena el valor asociado a la clave "tituloTecnica"
            // (definido en la ruta); por lo tanto, si se hace "click" en la primera imagen el par
            // clave-valor sería algo así: "tituloTecnica: Técnicas Básicas".
            val apartadoTecnica = backStackEntry.arguments?.getString("tituloTecnica")
            //Por último se pasa ese string a la función para mostrar la pantalla
            PantallaTecnica(tecnicaTitle = apartadoTecnica)
        }
    }
}

// Sirve para poder utilizar "Material Experimental", en este caso es el "TopAppBar"
@OptIn(ExperimentalMaterial3Api::class)
@Composable // Utilizamos Composable para decir que estamos definiendo una UI
// Declaramos una función que será la pantalla principal ("Main") de la aplicación
fun MainScreen(tecnicas: List<Tecnica>, navController: NavHostController) {
    // Se crea un Scaffold que básicamente es un panel invisible que sirve para colocar los elementos
    // de una forma ordenada
    Scaffold(
        // Añadimos un modificador para que el Scaffold ocupe toda la pantalla
        modifier = Modifier.fillMaxSize(),
        topBar = {  // Le añadimos un "topBar"
            CenterAlignedTopAppBar( // Centramos el contenido del "topBar"
                title = {   // Creamos un título en el "topBar"
                    Text(
                        text = "Wrestling", // texto del título
                        fontWeight = FontWeight.Bold,   // fuente en negrita
                        fontSize = 38.sp    // Tamaño de la fuente
                    )
                }
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
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)
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
                            // Cuando se hace click sobre él, se pasa la ruta con el título del objeto
                            // a partir del que se ha generado la caja "imagen + texto". De ese modo se
                            // "viaja" a la pantalla que tenga asignada esa ruta
                            navController.navigate(route = "detalle/${tecnica.title}")
                        }
                    )
                    //Despues de cada contenedor (imagen + texto) se añade un espacio
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    )
}

@Composable //Utilizamos Composable para decir que estamos definiendo una UI
// Esta función aún es muy primitiva pero es la que define la pantalla que se abre cuando se hace
// click en alguna de las técnicas
fun PantallaTecnica(tecnicaTitle: String?) {
    Box(    //Se crea una caja
        modifier = Modifier
            .fillMaxSize()  //Se adapta para que cubra toda la pantalla
            .padding(16.dp),    //Se le asigna un padding general
        //Se centra el contenido al centro tanto vertical como horizontalmente
        contentAlignment = Alignment.Center
    ) { //Contenido de la caja:
        Text(   //Se crea un texto
            // Si no se ha pasado ningun argumento para el título se pone por defecto el texto
            // "Detalles de la técnica".
            text = tecnicaTitle ?: "Detalles de la técnica",
            // Se aplica un estilo definido para las cabeceras que ya proporciona el objeto
            // "MaterialTheme"
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold    // Se pone en negrita
        )
    }
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
            .padding(16.dp),    //Se añade un padding general
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(  //Se añade la imagen
            // Se coloca la imagen con la ruta que se pasa como argumento
            painter = painterResource(id = imageRes),
            contentDescription = text,  // Se añade el texto como descripcion de la imagen
            modifier = Modifier
                .fillMaxWidth() // Se ajusta para que ocupe el ancho completo
        )
        Text(   //Se añade el texto a continuación
            text = text,    //Se añade el texto que se pasa como argumento
            style = MaterialTheme.typography.bodyLarge, //Se pone un estilo predefinido
            modifier = Modifier
                .padding(top = 8.dp)    //Un padding superior para separarlo de la imagen
                .fillMaxWidth() //Se ajusta al ancho disponible
                .wrapContentWidth(Alignment.CenterHorizontally),    //Se centra horizontalmente
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val tecnicasList = listOf(
        Tecnica(R.drawable.tecnicas_basicas, title = "Técnicas Básicas"),
        Tecnica(R.drawable.tecnicas_avanzadas, title = "Técnicas Avanzadas"),
        Tecnica(R.drawable.gran_amplitud, title = "Técnicas de Gran Amplitud"),
        Tecnica(R.drawable.tecnicas_suelo, title = "Técnicas de Suelo")
    )
    WrestlingAppTheme {
        MainScreen(tecnicas = tecnicasList, navController = rememberNavController())
    }
}