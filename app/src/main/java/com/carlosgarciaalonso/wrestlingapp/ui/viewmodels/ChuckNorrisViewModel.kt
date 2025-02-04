package com.carlosgarciaalonso.wrestlingapp.ui.viewmodels

import android.text.Html.ImageGetter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosgarciaalonso.wrestlingapp.data.network.ChuckNorrisService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChuckNorrisViewModel @Inject constructor(
    private val service: ChuckNorrisService
) : ViewModel() {

    // StateFlow para mostrar el chiste
    private val _consejo = MutableStateFlow("Cargando...")
    val consejo: StateFlow<String> = _consejo

    // StateFlow para mostrar la imagen
    private val _imagenUrl = MutableStateFlow("")
    val imagenUrl: StateFlow<String> = _imagenUrl

    init {
        // Llamada inicial al servicio para obtener un chiste
        fetchOtroConsejo()
    }

    //Solicitar un chiste a la API:
    fun fetchOtroConsejo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val randomJoke = service.getRandomJoke()
                _consejo.value = randomJoke.broma
                _imagenUrl.value = randomJoke.image  // Guardar la URL de la imagen
            } catch (e: Exception) {
                //Si no hay conexión a internet se muestra este mensaje
                _consejo.value = "No hay conexión a Internet en este momento."
                _imagenUrl.value = "" // Si hay error, limpiar la imagen
            }
        }
    }
}