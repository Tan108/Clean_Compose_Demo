package com.tan.feature.dashboard.data.api

import com.tan.feature.dashboard.data.dto.DashboardResponseDto
import retrofit2.http.GET

interface DashboardApiService {

    @GET("/v3/football/fixtures")
    suspend fun getDashboardData(): DashboardResponseDto
}