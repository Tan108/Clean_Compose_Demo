package com.tan.feature.dashboard.domain.repository

import com.tan.feature.dashboard.domain.model.DashboardModel
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun getDashboardData(): Flow<List<DashboardModel>>
    suspend fun refreshDashboard()
}