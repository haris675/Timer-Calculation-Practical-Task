package com.mart2global.timecalculation.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mart2global.timecalculation.data.TimerData

@Dao
interface TaskDao {

    @Query("SELECT * FROM timer_table")
    suspend fun getAllTasks(): List<TimerData>

    @Insert
    suspend fun insertData(data: TimerData)

}