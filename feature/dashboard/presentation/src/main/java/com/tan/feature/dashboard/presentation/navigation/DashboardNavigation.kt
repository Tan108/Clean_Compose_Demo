package com.tan.feature.dashboard.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tan.feature.dashboard.presentation.route.DashboardRoute
import com.tan.feature.dashboard.presentation.ui.screen.DashboardScreen

fun NavGraphBuilder.dashboardGraph(
    onOpenDetails: (String) -> Unit
) {
    composable<DashboardRoute> {
        DashboardScreen(
            onItemClick = { name ->
                onOpenDetails(
                    name
                )
            }
        )
    }
}