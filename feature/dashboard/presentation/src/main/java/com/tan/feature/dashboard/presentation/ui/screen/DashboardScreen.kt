package com.tan.feature.dashboard.presentation.ui.screen

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tan.feature.dashboard.domain.model.DashboardModel
import com.tan.feature.dashboard.presentation.uistate.DashboardUiState
import com.tan.feature.dashboard.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    onItemClick: (String) -> Unit,
) {
    val viewmodel: DashboardViewModel = hiltViewModel()

    val dashboardUiState by viewmodel.uiState.collectAsStateWithLifecycle()

    when (val state = dashboardUiState) {
        DashboardUiState.Loading ->
            DashboardLoader()

        DashboardUiState.Empty ->
            DashboardMessage("No List Available")

        is DashboardUiState.Error ->
            DashboardMessage(state.message)

        is DashboardUiState.Success ->
            DashboardContent(
                data = state.data,
                onItemClick = onItemClick
            )
    }

}

@Composable
fun DashboardContent(
    data: List<DashboardModel>,
    onItemClick: (String) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = data,
            key = { it.name }
        ) { dashboardModel ->
            DashboardCardItem(
                dashboardModel = dashboardModel,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun DashboardCardItem(
    dashboardModel: DashboardModel,
    onItemClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier.clickable {
            onItemClick(dashboardModel.name)
        }
    ) {
        Text(text = dashboardModel.name)
        Text(text = dashboardModel.resultInfo)
    }
}

@Composable
fun DashboardLoader() {

    val infiniteTransition = rememberInfiniteTransition(label = "loader")

    val color by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.primary,
        targetValue = MaterialTheme.colorScheme.tertiary,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "loaderColor"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(60.dp),
            color = color,
            strokeWidth = 6.dp,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun DashboardMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}