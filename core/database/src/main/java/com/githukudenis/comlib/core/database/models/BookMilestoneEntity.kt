package com.githukudenis.comlib.core.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookMilestoneEntity(
    @PrimaryKey(autoGenerate = false)
    val bookId: String,
    val bookName: String,
    val startDate: Long,
    val endDate: Long
)
