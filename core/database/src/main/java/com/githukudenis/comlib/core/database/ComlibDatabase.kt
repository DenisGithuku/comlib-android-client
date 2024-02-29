package com.githukudenis.comlib.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.githukudenis.comlib.core.database.dao.BookMilestoneDao
import com.githukudenis.comlib.core.database.models.BookMilestoneEntity

@Database(entities = [BookMilestoneEntity::class], version = 2, exportSchema = true)
internal abstract class ComlibDatabase: RoomDatabase() {
    abstract fun bookMilestoneDao(): BookMilestoneDao
}