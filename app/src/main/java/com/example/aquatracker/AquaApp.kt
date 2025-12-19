package com.example.aquatracker

import android.app.Application
import androidx.room.Room
import com.example.aquatracker.repository.database.AquaDatabase

class AquaApp : Application() {

    lateinit var database: AquaDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AquaDatabase::class.java,
            "aqua_db"
        ).build()
    }
}