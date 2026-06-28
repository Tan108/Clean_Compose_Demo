package com.tan.feature.dashboard.domain.usecase

import com.tan.feature.dashboard.domain.repository.DashboardRepository
import javax.inject.Inject

class RefreshDashboardUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository,
) {

    suspend operator fun invoke(){
        dashboardRepository.refreshDashboard()
    }
}