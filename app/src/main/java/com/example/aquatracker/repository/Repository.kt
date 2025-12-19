package com.example.aquatracker.repository

import com.example.aquatracker.repository.dataClass.RepoAquaItem
import com.example.aquatracker.repository.database.AquaItemDao
import com.example.aquatracker.repository.database.AquaItemEntity
import com.example.aquatracker.viewModel.dataClass.AquaItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class Repository(private val aquaItemDao: AquaItemDao) {

    fun getColdRepoAquaItems(): List<RepoAquaItem> {
        return listOf(
            RepoAquaItem(LocalDate.of(2024, 2, 15), 10.0),
            RepoAquaItem(LocalDate.of(2024, 3, 1), 12.5),
            RepoAquaItem(LocalDate.of(2024, 3, 15), 8.0),
            RepoAquaItem(LocalDate.of(2024, 4, 10), 15.0),
            RepoAquaItem(LocalDate.of(2024, 5, 5), 9.5)
        )
    }

    fun getHotRepoAquaItems(): List<RepoAquaItem> {
        return listOf(
            RepoAquaItem(LocalDate.of(2024, 2, 15), 10.0),
            RepoAquaItem(LocalDate.of(2024, 3, 1), 12.5),
            RepoAquaItem(LocalDate.of(2024, 3, 15), 8.0),
            RepoAquaItem(LocalDate.of(2024, 4, 10), 15.0),
            RepoAquaItem(LocalDate.of(2024, 5, 5), 9.5)
        )
    }

    suspend fun addItem(item: AquaItemEntity) {
        aquaItemDao.insert(item)
    }

    suspend fun deleteItem(aquaItemId: Long) {
        aquaItemDao.deleteById(aquaItemId)
    }

    fun getHotWaterItems(): Flow<List<AquaItemEntity>> =
        aquaItemDao.getItemsByType(isHot = true)

    fun getColdWaterItems(): Flow<List<AquaItemEntity>> =
        aquaItemDao.getItemsByType(isHot = false)

    suspend fun updateById(itemId: Long, itemValue: Double, itemDate: Long) {
        aquaItemDao.updateById(id = itemId, value = itemValue, date = itemDate)
    }

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getRepository(aquaItemDao: AquaItemDao): Repository {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Repository(aquaItemDao)
                INSTANCE = instance
                return instance
            }
        }
    }
}