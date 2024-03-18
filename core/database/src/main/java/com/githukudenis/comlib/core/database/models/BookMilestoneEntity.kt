package com.githukudenis.comlib.core.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_on_streak")
data class BookMilestoneEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val bookId: String? = null,
    val bookName: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
    val pages: Int? = null
)
