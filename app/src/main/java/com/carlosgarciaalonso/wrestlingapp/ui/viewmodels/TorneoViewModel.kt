package com.carlosgarciaalonso.wrestlingapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosgarciaalonso.wrestlingapp.data.repository.TorneoRepository
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.combinados.TournamentWithCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel para la pantalla de Torneos. Maneja la solicitud de datos desde TorneoRepository y
// los refleja como StateFlow para la UI.
@HiltViewModel
class TorneoViewModel @Inject constructor(
    private val repository: TorneoRepository
) : ViewModel() {

    //Convertimos el Flow de Room en un StateFlow, para poder usar collectAsState() en la UI.
    val tournaments: StateFlow<List<TournamentWithCategories>> =
        repository.getAllTournamentsWithCategoriesFlow()
            // stateIn() crea un StateFlow a partir de un Flow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    init {
        // Insertar datos iniciales en segundo plano.
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertInitialDataIfNeeded()
        }
    }
}

