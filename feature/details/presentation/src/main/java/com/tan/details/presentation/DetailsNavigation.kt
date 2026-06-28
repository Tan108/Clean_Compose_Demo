package com.tan.details.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

fun NavGraphBuilder.detailsGraph() {
    composable<DetailsRoute> {
        val route = it.toRoute<DetailsRoute>()
        DetailsScreen(
            name = route.title
        )
    }
}