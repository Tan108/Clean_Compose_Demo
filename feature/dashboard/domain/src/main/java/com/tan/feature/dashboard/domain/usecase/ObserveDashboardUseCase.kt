package com.tan.feature.dashboard.domain.usecase

import com.tan.feature.dashboard.domain.model.DashboardModel
import com.tan.feature.dashboard.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveDashboardUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository,
) {

     operator fun invoke(): Flow<List<DashboardModel>>{
        return dashboardRepository.getDashboardData()
    }
}