package com.tan.feature.dashboard.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DashboardModel(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val resultInfo: String,
)
