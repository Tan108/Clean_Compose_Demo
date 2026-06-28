package com.tan.feature.dashboard.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tan.feature.dashboard.domain.model.DashboardModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DashboardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDashboardData(dashboardModels: List<DashboardModel>)

    @Delete
    suspend fun deleteDashboardData(dashboardModel: DashboardModel)

    @Query("SELECT * from dashboardmodel")
    fun getAllDashboardData(): Flow<List<DashboardModel>>

    @Update
    suspend fun updateDashboardData(dashboardModel: DashboardModel)
}