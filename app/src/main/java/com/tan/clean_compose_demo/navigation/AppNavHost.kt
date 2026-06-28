package com.tan.clean_compose_demo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tan.details.presentation.DetailsRoute
import com.tan.details.presentation.detailsGraph
import com.tan.feature.dashboard.presentation.navigation.dashboardGraph
import com.tan.feature.dashboard.presentation.route.DashboardRoute

@Composable
fun AppNavHost(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = DashboardRoute
    ) {

        dashboardGraph(
            onOpenDetails = { name ->
                navController.navigate(
                    route = DetailsRoute(
                        title = name
                    )
                )
            }
        )

        detailsGraph()
    }
}