package com.tan.feature.dashboard.presentation.uistate

import com.tan.feature.dashboard.domain.model.DashboardModel

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data class Success(val data: List<DashboardModel>) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
    data object Empty : DashboardUiState
}

sealed interface DashboardIntent {
    data object GetDashboard : DashboardIntent
}
