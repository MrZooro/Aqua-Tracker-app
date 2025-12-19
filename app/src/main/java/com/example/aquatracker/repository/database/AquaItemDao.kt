package com.example.aquatracker.repository.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AquaItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AquaItemEntity)

    @Query("DELETE FROM aqua_items WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("""
        SELECT * FROM aqua_items
        WHERE isHot = :isHot
        ORDER BY date ASC
    """)
    fun getItemsByType(isHot: Boolean): Flow<List<AquaItemEntity>>

    @Query("""
    UPDATE aqua_items
    SET value = :value, date = :date
    WHERE id = :id
    """)
    suspend fun updateById(id: Long, value: Double, date: Long)
}