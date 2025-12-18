package com.example.aquatracker.viewModel.uiState

import com.example.aquatracker.viewModel.dataClass.AquaItem

data class HotWaterUiState(
    val hotAquaList: List<AquaItem> = emptyList()
)