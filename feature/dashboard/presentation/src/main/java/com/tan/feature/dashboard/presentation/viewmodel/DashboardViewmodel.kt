package com.tan.feature.dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tan.feature.dashboard.domain.usecase.DashboardUseCase
import com.tan.feature.dashboard.presentation.uistate.DashboardIntent

import com.tan.feature.dashboard.presentation.uistate.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewmodel @Inject constructor(
    private val dashboardUseCase: DashboardUseCase,
) : ViewModel() {

    private var _dashboardUiState = MutableStateFlow(DashboardUiState())
    val dashboardUiState = _dashboardUiState.asStateFlow()

    private val exception = CoroutineExceptionHandler { context, throwable -> }

    fun onIntent(dashboardIntent: DashboardIntent) {
        when (dashboardIntent) {
            DashboardIntent.GetDashboard -> getDashboardData()
        }
    }

    private fun getDashboardData() {
        viewModelScope.launch(exception) {
            _dashboardUiState.update {
                it.copy(loading = true)
            }
            try {
                val data = dashboardUseCase.invoke()
                _dashboardUiState.update {
                    when {
                        data.isEmpty() -> it.copy(
                            loading = false,
                            error = "No data found"
                        )

                        else -> it.copy(
                            loading = false,
                            data = data,
                        )
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _dashboardUiState.update {
                    it.copy(loading = false, error = e.message ?: "Unknown error")
                }
            }
        }
    }

}