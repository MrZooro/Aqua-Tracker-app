package com.example.aquatracker.viewModel.uiState

import com.example.aquatracker.viewModel.dataClasses.AquaItem

data class ColdWaterUiState(
    val coldAquaList: List<AquaItem> = emptyList()
)
