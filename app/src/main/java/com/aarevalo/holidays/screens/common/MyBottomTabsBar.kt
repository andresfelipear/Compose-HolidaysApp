package com.aarevalo.holidays.screens.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MyBottomTabsBar(
    bottomTabs: List<BottomTab>,
    currentTab: BottomTab?,
    onTabSelected: (BottomTab) -> Unit
){

    NavigationBar {
        bottomTabs.forEach { bottomTab ->
            NavigationBarItem(
                selected = currentTab == bottomTab,
                onClick = { onTabSelected(bottomTab) },
                icon = {
                    Icon(bottomTab.icon!!, contentDescription = bottomTab.title)
                },
                label = {
                    Text(bottomTab.title)
                },
            )
        }
    }

}