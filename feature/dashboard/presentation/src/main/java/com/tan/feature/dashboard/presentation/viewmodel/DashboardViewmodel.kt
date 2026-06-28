package com.tan.feature.dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tan.feature.dashboard.domain.usecase.ObserveDashboardUseCase
import com.tan.feature.dashboard.domain.usecase.RefreshDashboardUseCase

import com.tan.feature.dashboard.presentation.uistate.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val observeDashboardUseCase: ObserveDashboardUseCase,
    private val refreshDashboardUseCase: RefreshDashboardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeDashboard()
        refresh()
    }

    private fun observeDashboard() {
        viewModelScope.launch {
            observeDashboardUseCase().collect { dashboard ->

                _uiState.update {
                    it.copy(
                        data = dashboard,
                        error = null
                    )
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {

            _uiState.update {
                it.copy(loading = true)
            }

            try {
                refreshDashboardUseCase()
            } catch (e: Exception) {

                _uiState.update {
                    it.copy(error = e.message ?: "Unknown error")
                }

            } finally {

                _uiState.update {
                    it.copy(loading = false)
                }
            }
        }
    }
}