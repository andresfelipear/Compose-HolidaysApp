package com.aarevalo.holidays.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.aarevalo.holidays.navigation.Route

data class DrawerItem(
    val route: Route,
    val icon: ImageVector,
    val title: Int
)