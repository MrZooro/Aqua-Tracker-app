package com.example.aquatracker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aquatracker.AquaApp
import com.example.aquatracker.repository.Repository
import com.example.aquatracker.repository.database.AquaItemEntity
import com.example.aquatracker.util.DateFormatter
import com.example.aquatracker.viewModel.dataClass.AquaItem
import com.example.aquatracker.viewModel.uiState.ColdWaterUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ColdWaterViewModel(application: Application) : AndroidViewModel(application) {

    private val mutableUiState = MutableStateFlow(ColdWaterUiState())
    val uiState = mutableUiState.asStateFlow()

    private val repository = Repository.getRepository(
        (application as AquaApp)
            .database
            .aquaItemDao()
    )

    init {
        val newColdAquaList: MutableList<AquaItem> = mutableListOf()
        var prevValue: Double? = null

        viewModelScope.launch(Dispatchers.IO) {
            repository.getColdWaterItems().collect { aquaList ->
                aquaList.forEach { aquaItem ->
                    newColdAquaList.add(
                        AquaItem.fromRepoAquaItem(
                            aquaItem,
                            DateFormatter.fullDate,
                            delta = (prevValue?.plus(aquaItem.value)) ?: 0.0
                        )
                    )
                    prevValue = -aquaItem.value
                }

                mutableUiState.update { currentState ->
                    currentState.copy(coldAquaList = newColdAquaList.toList())
                }
            }
        }
    }

    fun deleteAquaItem(aquaItemId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(aquaItemId)
        }
    }

    fun insertAquaItem(itemDate: Long, itemValue: Double) {
        viewModelScope.launch {
            repository.addItem(
                AquaItemEntity(
                    date = itemDate,
                    value = itemValue,
                    isHot = false
                )
            )
        }
    }

    fun updateById(itemId: Long, itemValue: Double, itemDate: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateById(itemId, itemValue, itemDate)
        }
    }
}