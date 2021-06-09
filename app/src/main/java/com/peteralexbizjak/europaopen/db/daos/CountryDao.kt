package com.peteralexbizjak.europaopen.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.peteralexbizjak.europaopen.db.entities.CountryEntity

@Dao
internal interface CountryDao {
    @Insert
    suspend fun storeCountries(vararg countries: CountryEntity)

    @Query("select * from country order by id asc")
    suspend fun retrieveAllCountries(): List<CountryEntity>

    @Query("select * from country where direction = :direction order by id asc")
    suspend fun retrieveCountriesByDirection(direction: String): List<CountryEntity>

    @Query("select count(*) from country")
    suspend fun retrieveTableSize(): Int

    @Query("delete from country")
    suspend fun clearTable()
}