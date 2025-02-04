package com.carlosgarciaalonso.wrestlingapp.data.network



import android.text.Html.ImageGetter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query



interface ChuckNorrisService {
    @GET("jokes/random")
    suspend fun getRandomJoke() : RandomJokeReponse

//  @GET("jokes/search")
//  suspend fun getRandomJoke(@Query("query") query : String) : [Insertar nueva clase]
//  Falta la segunda clase para manejar este nuevo json

}

// Lo ideal es crear la clase aquí mismo para indicar que es tal cual la información que se extrae
// de internet
@Serializable
data class RandomJokeReponse(
    @SerialName("icon_url") val image : String,
    @SerialName("value") val broma : String
)

/**
 * Ejemplo de respuesta de la api:
 * {
 * "categories":["explicit"],
 * "created_at":"2020-01-05 13:42:29.855523",
 * "icon_url":"https://api.chucknorris.io/img/avatar/chuck-norris.png",
 * "id":"g_egHSh6RLqqaVxQqqo0ZA",
 * "updated_at":"2020-01-05 13:42:29.855523",
 * "url":"https://api.chucknorris.io/jokes/g_egHSh6RLqqaVxQqqo0ZA",
 * "value":"Not even a fictional Chuck Norris elf would ever sing \"Dawn we now our gay apparel, Fa-la-la-la-la-la-la-la-la\"."}
 * */