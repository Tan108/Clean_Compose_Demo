package com.tan.feature.dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tan.feature.dashboard.domain.usecase.ObserveDashboardUseCase
import com.tan.feature.dashboard.domain.usecase.RefreshDashboardUseCase

import com.tan.feature.dashboard.presentation.uistate.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val observeDashboardUseCase: ObserveDashboardUseCase,
    private val refreshDashboardUseCase: RefreshDashboardUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var isRefreshing = true

    init {
        observeDashboard()
        refresh()
    }

    private fun observeDashboard() {
        observeDashboardUseCase()
            .onEach { dashboard ->

                _uiState.value = when {
                    dashboard.isNotEmpty() ->
                        DashboardUiState.Success(dashboard)

                    isRefreshing ->
                        DashboardUiState.Loading

                    else ->
                        DashboardUiState.Empty
                }
            }
            .catch { throwable ->
                _uiState.value =
                    DashboardUiState.Error(
                        throwable.message ?: "Unknown error"
                    )
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshing = true

            try {
                refreshDashboardUseCase()
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(
                    e.message ?: "Unknown error"
                )
            } finally {
                isRefreshing = false
            }
        }
    }
}