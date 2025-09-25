package com.mart2global.timecalculation.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


object Utils {

    fun getCurrentData(): String {
        val c: Date = Calendar.getInstance().getTime()
        println("Current time => $c")

        val df: SimpleDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        return df.format(c)
    }

}