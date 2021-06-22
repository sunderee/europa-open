package com.peteralexbizjak.europaopen.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regions")
internal data class RegionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val regionID: Int = 0,

    @ColumnInfo(name = "country")
    val countryCode: String,

    @ColumnInfo(name = "region")
    val regionName: String,

    @ColumnInfo(name = "region")
    val regionColor: String
)