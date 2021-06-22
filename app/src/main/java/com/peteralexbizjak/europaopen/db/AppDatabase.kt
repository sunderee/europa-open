package com.peteralexbizjak.europaopen.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peteralexbizjak.europaopen.db.daos.CountryDao
import com.peteralexbizjak.europaopen.db.daos.RegionDao
import com.peteralexbizjak.europaopen.db.entities.CountryEntity
import com.peteralexbizjak.europaopen.db.entities.RegionEntity

@Database(
    entities = [CountryEntity::class, RegionEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun regionDao(): RegionDao
}