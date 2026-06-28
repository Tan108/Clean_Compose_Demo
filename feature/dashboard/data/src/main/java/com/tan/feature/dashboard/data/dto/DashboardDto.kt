package com.tan.feature.dashboard.data.dto

import com.google.gson.annotations.SerializedName
import com.tan.feature.dashboard.domain.model.DashboardModel

data class DashboardResponseDto(
    @SerializedName("data") val dashboardData: List<DashboardData>,
)

data class DashboardData(
    @SerializedName("name") val name: String,
    @SerializedName("result_info") val resultInfo: String
) {
    fun mapToDomain(): DashboardModel {
        return DashboardModel(
            name = name,
            resultInfo = resultInfo,
        )
    }
}