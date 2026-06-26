package com.tan.feature.dashboard.presentation.uistate

import com.tan.feature.dashboard.domain.model.DashboardModel

data class DashboardUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: List<DashboardModel> = emptyList(),
)

sealed interface DashboardIntent {
    data object GetDashboard : DashboardIntent
}
