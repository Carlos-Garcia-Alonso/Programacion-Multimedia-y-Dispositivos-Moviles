package com.carlosgarciaalonso.wrestlingapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosgarciaalonso.wrestlingapp.data.repository.TorneoRepository
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.combinados.TournamentWithCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla de Torneos. Maneja la carga de datos desde
 * TorneoRepository y los expone como StateFlow para la UI.
 */
@HiltViewModel
class TorneoViewModel @Inject constructor(
    private val repository: TorneoRepository
) : ViewModel() {

    // Encapsulamos la lista de torneos en un StateFlow
    private val _tournaments = MutableStateFlow<List<TournamentWithCategories>>(emptyList())
    val tournaments: StateFlow<List<TournamentWithCategories>> = _tournaments

    init {
        // Cargar los torneos al inicializar el ViewModel
        loadTournaments()
    }

    private fun loadTournaments() {
        // Se lanza una corrutina en el scope del ViewModel
        viewModelScope.launch(Dispatchers.IO) {

            //delay(5000)

            // Verificar si la BD está vacía y, de ser así, rellenarla
            repository.insertInitialDataIfNeeded()

            // Consultar la lista de torneos con categorías
            val data = repository.getAllTournamentsWithCategories()

            // Actualizar el StateFlow con los datos obtenidos
            _tournaments.value = data
        }
    }
}

