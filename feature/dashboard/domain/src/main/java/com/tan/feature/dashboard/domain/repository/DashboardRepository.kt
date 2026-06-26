package com.tan.feature.dashboard.domain.repository

import com.tan.feature.dashboard.domain.model.DashboardModel

interface DashboardRepository {
    suspend fun getDashboardData(): List<DashboardModel>
}