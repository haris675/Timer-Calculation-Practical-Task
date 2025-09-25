package com.mart2global.timecalculation.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer_table")
data class TimerData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timerList: ArrayList<InnerTimeData>,
    var isExpanded: Boolean = false
)

data class InnerTimeData(
    val date: String? = null,
    val time: String? = null
)