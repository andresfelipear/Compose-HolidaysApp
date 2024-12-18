package com.aarevalo.holidays.screens.common

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyBottomTabsBar(
    bottomTabs: List<BottomTab>,
    currentBottomTab: BottomTab?,
    onTabClicked: (BottomTab) -> Unit
){
    NavigationBar() {
        bottomTabs.forEachIndexed { _, bottomTab ->
            NavigationBarItem(
                alwaysShowLabel = true,
                icon = { Icon(bottomTab.icon!!, contentDescription = bottomTab.title)},
                label = { Text(bottomTab.title)},
                selected = bottomTab == currentBottomTab,
                onClick = { onTabClicked(bottomTab) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomTabsBarPreview(){
    MyBottomTabsBar(
        bottomTabs = listOf(
            BottomTab.Main,
            BottomTab.Holidays
        ),
        currentBottomTab = BottomTab.Main,
        onTabClicked = {}
    )
}
