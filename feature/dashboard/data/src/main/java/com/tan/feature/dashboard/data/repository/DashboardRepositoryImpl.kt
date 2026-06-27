package com.tan.feature.dashboard.data.repository

import com.tan.feature.dashboard.data.api.DashboardApiService
import com.tan.feature.dashboard.domain.model.DashboardModel
import com.tan.feature.dashboard.domain.repository.DashboardRepository
import jakarta.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val dashboardApiService: DashboardApiService
) : DashboardRepository {

    override suspend fun getDashboardData(): List<DashboardModel> {
        return dashboardApiService.getDashboardData().fixtureData.map { it.mapToDomain() }
    }
}