package com.tan.feature.dashboard.data.repository

import com.tan.core.common.annotations.IoDispatcher
import com.tan.feature.dashboard.data.api.DashboardApiService
import com.tan.feature.dashboard.data.local.DashboardDao
import com.tan.feature.dashboard.domain.model.DashboardModel
import com.tan.feature.dashboard.domain.repository.DashboardRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class DashboardRepositoryImpl @Inject constructor(
    private val dashboardApiService: DashboardApiService,
    private val dashboardDao: DashboardDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : DashboardRepository {

    override fun getDashboardData(): Flow<List<DashboardModel>> {
        return dashboardDao.getAllDashboardData()
            .flowOn(ioDispatcher)
    }

    override suspend fun refreshDashboard() = withContext(ioDispatcher) {
        val response = dashboardApiService.getDashboardData()

        dashboardDao.insertDashboardData(
            dashboardModels = response.dashboardData.map { dto ->
                dto.mapToDomain()
            }
        )
    }
}