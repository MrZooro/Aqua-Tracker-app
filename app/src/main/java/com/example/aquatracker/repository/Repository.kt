package com.example.aquatracker.repository

import com.example.aquatracker.repository.dataClass.RepoAquaItem
import java.time.LocalDate

class Repository {

    fun getColdRepoAquaItems(): List<RepoAquaItem> {
        return listOf(
            RepoAquaItem(LocalDate.of(2024, 2, 15), 10.0),
            RepoAquaItem(LocalDate.of(2024, 3, 1), 12.5),
            RepoAquaItem(LocalDate.of(2024, 3, 15), 8.0),
            RepoAquaItem(LocalDate.of(2024, 4, 10), 15.0),
            RepoAquaItem(LocalDate.of(2024, 5, 5), 9.5)
        )
    }

    fun getHotRepoAquaItems() : List<RepoAquaItem> {
        return listOf(
            RepoAquaItem(LocalDate.of(2024, 2, 15), 10.0),
            RepoAquaItem(LocalDate.of(2024, 3, 1), 12.5),
            RepoAquaItem(LocalDate.of(2024, 3, 15), 8.0),
            RepoAquaItem(LocalDate.of(2024, 4, 10), 15.0),
            RepoAquaItem(LocalDate.of(2024, 5, 5), 9.5)
        )
    }

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getRepository(): Repository {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Repository()
                INSTANCE = instance
                return instance
            }
        }
    }
}