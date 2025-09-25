package com.mart2global.timecalculation.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mart2global.timecalculation.data.InnerTimeData
import java.lang.reflect.Type

class TypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromTimerList(timerList: ArrayList<InnerTimeData>?): String {
        return gson.toJson(timerList)
    }

    @TypeConverter
    fun toTimerList(data: String?): ArrayList<InnerTimeData> {
        if (data.isNullOrEmpty()) return arrayListOf()
        val type = object : TypeToken<ArrayList<InnerTimeData>>() {}.type
        return gson.fromJson(data, type)
    }

}