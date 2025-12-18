package com.example.aquatracker.viewModel

import androidx.lifecycle.ViewModel
import com.example.aquatracker.repository.Repository
import com.example.aquatracker.viewModel.dataClasses.AquaItem
import com.example.aquatracker.viewModel.uiState.ColdWaterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.format.DateTimeFormatter
import java.util.Locale

class ColdWaterViewModel : ViewModel() {

    private val dateFormat = DateTimeFormatter.ofPattern(
        "d MMMM yyyy",
        Locale.forLanguageTag("ru")
    )
    private val mutableUiState = MutableStateFlow(ColdWaterUiState())
    val uiState = mutableUiState.asStateFlow()

    private val repository = Repository.getRepository()

    init {
        val newColdAquaList: MutableList<AquaItem> = mutableListOf()
        var prevValue: Double? = null

        repository.getColdRepoAquaItems().forEach { repoAquaItem ->
            newColdAquaList.add(
                AquaItem.fromRepoAquaItem(
                    repoAquaItem,
                    dateFormat,
                    delta = prevValue ?: 0.0
                )
            )
            prevValue = repoAquaItem.value
        }

        mutableUiState.update { currentState ->
            currentState.copy(coldAquaList = newColdAquaList.toList())
        }
    }
}