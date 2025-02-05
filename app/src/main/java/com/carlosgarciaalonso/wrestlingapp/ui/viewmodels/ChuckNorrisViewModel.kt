package com.carlosgarciaalonso.wrestlingapp.ui.viewmodels

import android.text.Html.ImageGetter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosgarciaalonso.wrestlingapp.data.network.ChuckNorrisService
import com.carlosgarciaalonso.wrestlingapp.domain.ChuckNorrisJoke
import com.carlosgarciaalonso.wrestlingapp.domain.GetChuckNorrisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChuckNorrisViewModel @Inject constructor(
    private val getChuckNorris: GetChuckNorrisUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ChuckNorrisState>(ChuckNorrisState.Loading)
    val state = _state

    init {
        // Llamada inicial al servicio para obtener un chiste
        fetchOtroConsejo()
    }

    fun onJokeClicked() {

        fetchOtroConsejo()

    }


    //Solicitar un chiste a la API:
    private fun fetchOtroConsejo() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ChuckNorrisState.Loading
            try {
                _state.value = ChuckNorrisState.Success(getChuckNorris())
            } catch (e: Exception) {
                //Si no hay conexión a internet se muestra este mensaje
                _state.value = ChuckNorrisState.Error("No hay conexión a Internet en este momento.")
            }
        }
    }



}

sealed interface ChuckNorrisState {
    data object Loading : ChuckNorrisState
    data class Success(val joke : ChuckNorrisJoke) : ChuckNorrisState
    data class Error(val error : String) : ChuckNorrisState
}