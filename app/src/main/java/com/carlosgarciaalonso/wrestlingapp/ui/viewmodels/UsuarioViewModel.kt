package com.carlosgarciaalonso.wrestlingapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosgarciaalonso.wrestlingapp.domain.GetUsuarioUseCase
import com.carlosgarciaalonso.wrestlingapp.domain.InsertUsuarioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val getUsuario : GetUsuarioUseCase,
    private val insertUsuario : InsertUsuarioUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UsuarioState>(UsuarioState.Loading)
    val state : StateFlow<UsuarioState> = _state

    init {
        _state.value = UsuarioState.Loading

        viewModelScope.launch(Dispatchers.IO){
            try {

                _state.value = UsuarioState.Success(getUsuario())

            } catch (e:Exception) {

                _state.value = UsuarioState.Error("Error al acceder al usuario: $e")

            }
        }
    }

    fun onChange(usuario : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                insertUsuario(usuario)

                _state.value = UsuarioState.Success(getUsuario())

            } catch (e:Error){
                _state.value = UsuarioState.Error("Error al acceder al usuario: $e")
            }

        }

    }
}

sealed interface UsuarioState {
    object Loading : UsuarioState
    data class Success(val usuario : String?) : UsuarioState
    data class Error(val error : String) : UsuarioState
}