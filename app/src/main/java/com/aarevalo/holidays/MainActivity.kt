package com.aarevalo.holidays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aarevalo.holidays.screens.main.HomeScreenRoot
import com.aarevalo.holidays.ui.theme.HolidaysAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HolidaysAppTheme {
                HomeScreenRoot()
            }
        }
    }
}

