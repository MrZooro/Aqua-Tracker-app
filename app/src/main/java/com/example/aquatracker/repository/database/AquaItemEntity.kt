package com.example.aquatracker.repository.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.aquatracker.viewModel.dataClass.AquaItem
import java.time.LocalDate

/**
 * Хранит информацию, которая сохраняется в базе данных
 *
 * @param [id] Первичный ключ
 * @param [date] Дата, когда зафиксировали показание счётчика
 * @param [value] Показание счётчика
 * @param [isHot] если true, то счётчик отвечает за горячую воду,
 * если false, то счётчик отвечает за холодную
 */
@Entity(tableName = "aqua_items")
data class AquaItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long,
    val value: Double,
    val isHot: Boolean
)
