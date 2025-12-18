package com.example.aquatracker.viewModel

import androidx.lifecycle.ViewModel
import com.example.aquatracker.repository.Repository
import com.example.aquatracker.util.DateFormatter
import com.example.aquatracker.viewModel.dataClass.AquaItem
import com.example.aquatracker.viewModel.uiState.HotWaterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HotWaterViewModel : ViewModel() {

    private val mutableUiState = MutableStateFlow(HotWaterUiState())
    val uiState = mutableUiState.asStateFlow()

    private val repository = Repository.getRepository()

    init {
        val newHotAquaList: MutableList<AquaItem> = mutableListOf()
        var prevValue: Double? = null

        repository.getHotRepoAquaItems().forEach { repoAquaItem ->
            newHotAquaList.add(
                AquaItem.fromRepoAquaItem(
                    repoAquaItem,
                    DateFormatter.fullDate,
                    delta = (prevValue?.plus(repoAquaItem.value)) ?: 0.0
                )
            )
            prevValue = -repoAquaItem.value
        }

        mutableUiState.update { currentState ->
            currentState.copy(hotAquaList = newHotAquaList.toList())
        }
    }
}