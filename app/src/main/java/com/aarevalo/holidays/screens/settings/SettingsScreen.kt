package com.aarevalo.holidays.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenAction
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenState
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel

@Composable
fun SettingsScreenRoot(
    viewModel: CalendarScreenViewModel
){
    val state by viewModel.state.collectAsState()
    var isMenuExpanded by remember { mutableStateOf(false) }
    SettingsScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is CalendarScreenAction.UpdateCountry -> {
                    viewModel.onAction(action)
                }
            }
        },
        onMenuExpandedChange = { isMenuExpanded = it }
    )
}

@Composable
fun SettingsScreen(
    state: CalendarScreenState,
    onAction: (CalendarScreenAction) -> Unit = {},
    modifier: Modifier = Modifier,
    onMenuExpandedChange: (Boolean) -> Unit = {}
){
    LazyColumn(
        modifier = modifier
            .padding(start = 64.dp)
            .fillMaxSize()
    ) {
        item {
            Box(
                modifier = Modifier.clickable {
                    onMenuExpandedChange(true)
                }
            ) {
                Column {
                    Text(
                        text = "Country",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = state.country.name,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

}