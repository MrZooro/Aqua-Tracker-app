package com.example.aquatracker.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aquatracker.viewModel.ColdWaterViewModel

@Composable
fun ColdWaterScreen(paddingValues: PaddingValues) {
    val coldWaterViewModel: ColdWaterViewModel = viewModel()

    val uiState by coldWaterViewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(paddingValues), reverseLayout = true) {

        items(uiState.coldAquaList) { aquaItem ->
            CardItem(
                data = aquaItem,
                onDelete = {aquaItemId ->
                    coldWaterViewModel.deleteAquaItem(aquaItemId)
                },
                onEdit = {})
        }
    }
}