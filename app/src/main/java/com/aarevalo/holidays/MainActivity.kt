package com.aarevalo.holidays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aarevalo.holidays.screens.main.MainScreenRoot
import com.aarevalo.holidays.ui.theme.HolidaysAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HolidaysAppTheme {
                MainScreenRoot()
            }
        }
    }
}

