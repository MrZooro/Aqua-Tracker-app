package com.example.aquatracker.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aquatracker.viewModel.HotWaterViewModel
import com.example.aquatracker.viewModel.dataClass.AquaItem

@Composable
fun HotWaterScreen(paddingValues: PaddingValues) {

    val hotWaterViewModel: HotWaterViewModel = viewModel()
    val uiState by hotWaterViewModel.uiState.collectAsStateWithLifecycle()
    var editingItem by remember { mutableStateOf<AquaItem?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(paddingValues),
        reverseLayout = true
    ) {

        items(uiState.hotAquaList, key = { it.id }) { aquaItem ->
            CardItem(
                data = aquaItem,
                onDelete = { aquaItemId ->
                    hotWaterViewModel.deleteAquaItem(aquaItemId)
                },
                onEdit = { editingItem = aquaItem })
        }
    }

    editingItem?.let { aquaItem ->
        EditAquaDialog(
            aquaItem = aquaItem,
            onDismiss = { editingItem = null },
            onConfirm = { value, date ->
                hotWaterViewModel.updateById(
                    itemId = aquaItem.id,
                    itemValue = value,
                    itemDate = date
                )
                editingItem = null
            },
            onCancel = { editingItem = null }
        )
    }
}