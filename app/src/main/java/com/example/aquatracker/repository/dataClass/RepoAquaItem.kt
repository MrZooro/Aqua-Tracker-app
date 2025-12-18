package com.example.aquatracker.repository.dataClass

import java.time.LocalDate

/**
 * Хранит информацию, которая сохраняется в базе данных
 *
 * @param [date] Дата, когда зафиксировали показание счётчика
 * @param [value] Показание счётчика
 */
data class RepoAquaItem(
    val date: LocalDate,
    val value: Double
)
