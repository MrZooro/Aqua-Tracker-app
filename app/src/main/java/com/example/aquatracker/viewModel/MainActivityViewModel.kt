package com.example.aquatracker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aquatracker.AquaApp
import com.example.aquatracker.repository.Repository
import com.example.aquatracker.repository.database.AquaItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository.getRepository(
        (application as AquaApp)
            .database
            .aquaItemDao()
    )

    fun deleteAquaItem(aquaItemId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(aquaItemId)
        }
    }

    fun insertAquaItem(itemDate: Long, itemValue: Double, itemIsHot: Boolean) {
        viewModelScope.launch {
            repository.addItem(
                AquaItemEntity(
                    date = itemDate,
                    value = itemValue,
                    isHot = itemIsHot
                )
            )
        }
    }
}