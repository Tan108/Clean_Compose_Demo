package com.tan.feature.dashboard.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
fun DashboardScreen() {
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
            DashboardContent(data = state.data)
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

@Composable
fun DashboardLoader(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun DashboardMessage(message: String){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
       Text(message)
    }
}