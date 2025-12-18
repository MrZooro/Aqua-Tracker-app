package com.example.aquatracker.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aquatracker.viewModel.HotWaterViewModel

@Composable
fun HotWaterScreen(paddingValues: PaddingValues) {

    val hotWaterViewModel: HotWaterViewModel = viewModel()

    val uiState by hotWaterViewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {

        items(uiState.hotAquaList) { aquaItem ->
            CardItem(
                data = aquaItem,
                onDelete = {},
                onEdit = {})
        }
    }
}