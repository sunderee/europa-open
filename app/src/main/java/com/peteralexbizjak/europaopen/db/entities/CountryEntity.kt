package com.peteralexbizjak.europaopen.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
internal data class CountryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val countryID: Int? = null,

    @ColumnInfo(name = "short_name")
    val countryShortName: String,

    @ColumnInfo(name = "long_name")
    val countryLongName: String,

    @ColumnInfo(name = "direction")
    val appearsInDirection: String
)