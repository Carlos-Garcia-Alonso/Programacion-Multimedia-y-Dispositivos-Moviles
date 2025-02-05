package com.carlosgarciaalonso.wrestlingapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosgarciaalonso.wrestlingapp.data.repository.TorneoRepository
import com.carlosgarciaalonso.wrestlingapp.domain.GetTournamentUseCase
import com.carlosgarciaalonso.wrestlingapp.domain.TournamentWithCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel para la pantalla de Torneos. Maneja la solicitud de datos desde TorneoRepository y
// los refleja como StateFlow para la UI.
@HiltViewModel
class TorneoViewModel @Inject constructor(
    private val getTournament: GetTournamentUseCase
) : ViewModel() {

    // MutableStateFlow privado para manejar los estados
    private val _state = MutableStateFlow<TournamentsState>(TournamentsState.Loading)
    val state: StateFlow<TournamentsState> = _state // Exposición pública

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = TournamentsState.Loading // Emitimos estado de carga
            try {
                // Insertamos datos iniciales si es necesario
                getTournament.insertInitialDataIfNeeded()

                // Comenzamos a recolectar los datos en tiempo real
                getTournament.getTournamentFlow().collect { tournaments ->
                    // Emitimos el estado de éxito con los datos obtenidos
                    _state.value = TournamentsState.Success(tournaments)
                }
            } catch (e: Exception) {
                // En caso de error, emitimos un estado de error
                _state.value = TournamentsState.Error("Error al cargar los datos.")
            }
        }
    }
}

sealed interface TournamentsState {
    data object Loading : TournamentsState
    data class Success(val data: List<TournamentWithCategories>) : TournamentsState
    data class Error(val message: String) : TournamentsState
}

