package com.githukudenis.comlib.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.githukudenis.comlib.core.database.models.BookMilestoneEntity

@Dao
interface BookMilestoneDao {
    @Query("SELECT * FROM BookMilestoneEntity")
    fun getMilestone(): BookMilestoneEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setMilestone(milestone: BookMilestoneEntity)

    @Delete
    fun deleteMilestone(milestone: BookMilestoneEntity)

    @Update
    fun updateMilestone(milestone: BookMilestoneEntity)

}