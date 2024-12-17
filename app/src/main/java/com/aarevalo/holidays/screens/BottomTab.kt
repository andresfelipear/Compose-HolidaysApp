package com.aarevalo.holidays.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomTab(val icon: ImageVector?, var title:String) {
    data object Main : BottomTab(Icons.Rounded.Home, "Home")
    data object Holidays : BottomTab(Icons.AutoMirrored.Rounded.List, "Holidays")

    companion object {
        val BOTTOM_TABS = listOf(Main, Holidays)
    }
}



