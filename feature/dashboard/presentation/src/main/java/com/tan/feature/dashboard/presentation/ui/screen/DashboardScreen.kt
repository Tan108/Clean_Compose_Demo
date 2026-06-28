package com.tan.feature.dashboard.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tan.feature.dashboard.domain.model.DashboardModel
import com.tan.feature.dashboard.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen() {
    val viewmodel: DashboardViewModel = hiltViewModel()

    val dashboardUiState by viewmodel.uiState.collectAsStateWithLifecycle()

//    LaunchedEffect(Unit) {
//        viewmodel.onIntent(DashboardIntent.GetDashboard)
//    }

    when {
        dashboardUiState.loading -> {}
        !dashboardUiState.error.isNullOrEmpty() -> {}
        else -> {
            DashboardContent(data = dashboardUiState.data)
        }
    }

}

@Composable
fun DashboardContent(data: List<DashboardModel>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = data,
            key = { it.name }
        ) { dashboardModel ->
            DashboardCardItem(dashboardModel)
        }
    }
}

@Composable
fun DashboardCardItem(dashboardModel: DashboardModel) {
    Column {
        Text(text = dashboardModel.name)
        Text(text = dashboardModel.resultInfo)
    }
}