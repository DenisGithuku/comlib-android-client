
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
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
    @Query("SELECT * FROM book_on_streak limit 1") fun getMilestone(): BookMilestoneEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun setMilestone(milestone: BookMilestoneEntity)

    @Delete fun deleteMilestone(milestone: BookMilestoneEntity)

    @Update fun updateMilestone(milestone: BookMilestoneEntity)
}
