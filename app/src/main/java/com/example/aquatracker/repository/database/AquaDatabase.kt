package com.example.aquatracker.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [AquaItemEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(LocalDateConverter::class)
abstract class AquaDatabase : RoomDatabase() {
    abstract fun aquaItemDao(): AquaItemDao
}
