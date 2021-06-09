package com.peteralexbizjak.europaopen.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peteralexbizjak.europaopen.db.daos.CountryDao
import com.peteralexbizjak.europaopen.db.entities.CountryEntity

@Database(
    entities = [CountryEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}