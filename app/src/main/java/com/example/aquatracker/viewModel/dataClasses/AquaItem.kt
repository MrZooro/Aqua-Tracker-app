package com.example.aquatracker.viewModel.dataClasses

import com.example.aquatracker.repository.dataClasses.RepoAquaItem
import java.time.format.DateTimeFormatter

/**
 * Хранит информацию, которая потом отображается на карточках с показаниями счётчика
 *
 * @param [date] Дата, когда зафиксировали показание счётчика
 * @param [value] Показание счётчика
 * @param [delta] Отличие текущего показания от предыдущего
 */
data class AquaItem(
    val date: String,
    val value: Double,
    val delta: Double
) {

    companion object {

        /**
         * Создаёт объект типа AquaItem на основе объекта типа RepoAquaItem
         *
         * @param [repoAquaItem] Объект типа RepoAquaItem
         * @param [dateFormat] Формат строки, в который необходимо перевести дату
         * @param [delta] Отличие текущего показания от предыдущего
         */
        fun fromRepoAquaItem(
            repoAquaItem: RepoAquaItem,
            dateFormat: DateTimeFormatter,
            delta: Double
        ): AquaItem {
            return AquaItem(
                date = repoAquaItem.date.format(dateFormat),
                repoAquaItem.value,
                delta
            )
        }
    }
}