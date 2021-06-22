package com.peteralexbizjak.europaopen.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "indicators")
internal data class IndicatorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "indicator_id")
    val indicatorID: Int = 0,

    @ColumnInfo(name = "domain_id")
    val belongingDomainID: Int,

    @ColumnInfo(name = "name")
    val indicatorName: String,

    @ColumnInfo(name = "rules")
    val rules: String
)