package com.tan.clean_compose_demo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tan.feature.dashboard.data.local.DashboardDao
import com.tan.feature.dashboard.domain.model.DashboardModel

@Database(entities = [DashboardModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context) = Room
            .databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration(false)
            .build()
    }

    abstract fun getDashDao() : DashboardDao
}