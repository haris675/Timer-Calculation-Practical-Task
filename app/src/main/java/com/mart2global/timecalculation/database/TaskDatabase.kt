package com.mart2global.timecalculation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mart2global.timecalculation.data.TimerData

@Database(entities = [TimerData::class], version = 1, exportSchema = false)
@TypeConverters(com.mart2global.timecalculation.database.TypeConverters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "timer_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}