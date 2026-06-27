package com.tan.clean_compose_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tan.clean_compose_demo.ui.theme.Clean_Compose_DemoTheme
import com.tan.feature.dashboard.presentation.ui.screen.DashboardScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Clean_Compose_DemoTheme {
                DashboardScreen()
            }
        }
    }
}