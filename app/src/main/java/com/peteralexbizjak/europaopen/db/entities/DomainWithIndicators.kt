package com.peteralexbizjak.europaopen.db.entities

import androidx.room.Embedded
import androidx.room.Relation

internal data class DomainWithIndicators(
    @Embedded
    val domain: DomainEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "domain_id"
    )
    val indicators: List<IndicatorEntity>
)