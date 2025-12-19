package com.example.aquatracker.viewModel.dataClass

import com.example.aquatracker.repository.database.AquaItemEntity
import com.example.aquatracker.util.extension.formatDate
import java.time.format.DateTimeFormatter

/**
 * Хранит информацию, которая потом отображается на карточках с показаниями счётчика
 *
 * @param [dateStr] Дата, когда зафиксировали показание счётчика
 * @param [value] Показание счётчика
 * @param [delta] Отличие текущего показания от предыдущего
 */
data class AquaItem(
    val id: Long,
    val dateStr: String,
    val date: Long,
    val value: Double,
    val delta: Double
) {

    companion object {

        /**
         * Создаёт объект типа AquaItem на основе объекта типа RepoAquaItem
         *
         * @param [aquaItemEntity] Объект типа RepoAquaItem
         * @param [dateFormat] Формат строки, в который необходимо перевести дату
         * @param [delta] Отличие текущего показания от предыдущего
         */
        fun fromRepoAquaItem(
            aquaItemEntity: AquaItemEntity,
            dateFormat: DateTimeFormatter,
            delta: Double
        ): AquaItem {
            return AquaItem(
                id = aquaItemEntity.id,
                dateStr = aquaItemEntity.date.formatDate(dateFormat),
                date = aquaItemEntity.date,
                aquaItemEntity.value,
                delta
            )
        }
    }
}