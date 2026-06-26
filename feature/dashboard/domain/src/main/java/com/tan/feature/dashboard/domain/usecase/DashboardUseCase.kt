package com.tan.feature.dashboard.domain.usecase

import com.tan.feature.dashboard.domain.model.DashboardModel
import com.tan.feature.dashboard.domain.repository.DashboardRepository
import javax.inject.Inject

class DashboardUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository
) {

    suspend operator fun invoke(): List<DashboardModel>{
        return dashboardRepository.getDashboardData()
    }
}