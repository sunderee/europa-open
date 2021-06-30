package com.peteralexbizjak.europaopen.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "domains")
internal data class DomainEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val domainID: Int,

    @ColumnInfo(name = "name")
    val domainName: String
)