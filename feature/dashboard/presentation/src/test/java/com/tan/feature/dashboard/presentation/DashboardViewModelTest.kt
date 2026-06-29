package com.tan.feature.dashboard.presentation

import app.cash.turbine.test
import com.tan.feature.dashboard.domain.model.DashboardModel
import com.tan.feature.dashboard.domain.usecase.ObserveDashboardUseCase
import com.tan.feature.dashboard.domain.usecase.RefreshDashboardUseCase
import com.tan.feature.dashboard.presentation.uistate.DashboardUiState
import com.tan.feature.dashboard.presentation.viewmodel.DashboardViewModel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule

class DashboardViewModelTest : FunSpec() {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val observeDashboardUseCase = mockk<ObserveDashboardUseCase>()
    private val refreshDashboardUseCase = mockk<RefreshDashboardUseCase>()

    private lateinit var viewModel: DashboardViewModel

    init {

        beforeTest {
            clearAllMocks()
        }

        test("should emit Success when dashboard data is available") = runTest {

            val data = listOf(
                DashboardModel(
                    name = "Users",
                    resultInfo = "100"
                )
            )

            every {
                observeDashboardUseCase()
            } returns flowOf(data)

            viewModel = DashboardViewModel(
                observeDashboardUseCase,
                refreshDashboardUseCase
            )

            viewModel.uiState.test {

                awaitItem() shouldBe DashboardUiState.Loading

                awaitItem() shouldBe DashboardUiState.Success(data)

                cancelAndIgnoreRemainingEvents()
            }
        }

        test("should emit Empty when dashboard is empty") = runTest {

            every {
                observeDashboardUseCase()
            } returns flowOf(emptyList())

            viewModel = DashboardViewModel(
                observeDashboardUseCase,
                refreshDashboardUseCase
            )

            advanceUntilIdle()

            viewModel.uiState.value shouldBe DashboardUiState.Empty
        }

        test("should emit Error when observeDashboard throws") = runTest {

            every {
                observeDashboardUseCase()
            } returns flow {
                throw RuntimeException("Database Error")
            }

            viewModel = DashboardViewModel(
                observeDashboardUseCase,
                refreshDashboardUseCase
            )

            advanceUntilIdle()

            viewModel.uiState.value shouldBe
                    DashboardUiState.Error("Database Error")
        }

        test("refresh should call RefreshDashboardUseCase") = runTest {

            every {
                observeDashboardUseCase()
            } returns flowOf(emptyList())

            coEvery {
                refreshDashboardUseCase()
            } just Runs

            viewModel = DashboardViewModel(
                observeDashboardUseCase,
                refreshDashboardUseCase
            )

            viewModel.refresh()

            advanceUntilIdle()

            coVerify(exactly = 1) {
                refreshDashboardUseCase()
            }
        }

        test("refresh should emit Error when refresh fails") = runTest {

            every {
                observeDashboardUseCase()
            } returns flowOf(emptyList())

            coEvery {
                refreshDashboardUseCase()
            } throws RuntimeException("Network Error")

            viewModel = DashboardViewModel(
                observeDashboardUseCase,
                refreshDashboardUseCase
            )

            viewModel.refresh()

            advanceUntilIdle()

            viewModel.uiState.value shouldBe
                    DashboardUiState.Error("Network Error")
        }

        test("observeDashboard should collect multiple emissions") = runTest {

            val first = listOf(
                DashboardModel("A", "1")
            )

            val second = listOf(
                DashboardModel("A", "1"),
                DashboardModel("B", "2")
            )

            every {
                observeDashboardUseCase()
            } returns flow {

                emit(first)

                emit(second)
            }

            viewModel = DashboardViewModel(
                observeDashboardUseCase,
                refreshDashboardUseCase
            )

            viewModel.uiState.test {

                awaitItem() shouldBe DashboardUiState.Loading

                awaitItem() shouldBe DashboardUiState.Success(first)

                awaitItem() shouldBe DashboardUiState.Success(second)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}