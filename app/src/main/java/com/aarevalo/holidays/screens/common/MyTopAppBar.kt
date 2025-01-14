package com.aarevalo.holidays.screens.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aarevalo.holidays.R
import com.aarevalo.holidays.navigation.Route
import com.aarevalo.holidays.screens.common.navigation.NavigationAction
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(modifier: Modifier = Modifier,
                onAction: (NavigationAction) -> Unit,
                isHolidaysTab: Boolean,
                isRootRoute: Boolean,
                drawerState: DrawerState,
                currentRoute: State<Route?>) {
    var isFilterMenuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var filterMenuText by remember {
        mutableStateOf(context.getString(R.string.feat_calendar_filter_by_default))
    }

    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            MenuIcon(
                drawerState = drawerState,
                isRootRoute = isRootRoute,
                onAction = onAction
            )
        },
        title = {
            key(isHolidaysTab) {
                TitleContent(
                    isHolidaysTab = isHolidaysTab,
                    filterMenuText = filterMenuText,
                    isFilterMenuExpanded = isFilterMenuExpanded,
                    onFilterMenuExpandedChange = { isFilterMenuExpanded = it },
                    onFilterMenuTextChange = { filterMenuText = it },
                    onAction = onAction,
                    currentRoute = currentRoute
                )
            }
        }
    )
}

@Composable
private fun MenuIcon(
    drawerState: DrawerState,
    isRootRoute: Boolean,
    onAction: (NavigationAction) -> Unit
) {
    val scope = rememberCoroutineScope()
    if(isRootRoute){
        Icon(
            imageVector = Icons.Rounded.Menu,
            contentDescription = stringResource(id = R.string.menu),
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable {
                scope.launch {
                    drawerState.open()
                }
            }
        )
    } else {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.navigate_back_icon),
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable {
                onAction(NavigationAction.OnNavigateBack)
            }
        )
    }
}

@Composable
private fun TitleContent(
    isHolidaysTab: Boolean,
    filterMenuText: String,
    isFilterMenuExpanded: Boolean,
    onFilterMenuExpandedChange: (Boolean) -> Unit,
    onFilterMenuTextChange: (String) -> Unit,
    onAction: (NavigationAction) -> Unit,
    currentRoute: State<Route?>
) {
    val title = when(currentRoute.value){
        Route.Holidays -> stringResource(id = R.string.feat_holidays_title)
        Route.About -> stringResource(id = R.string.menu_about)
        Route.Settings -> stringResource(id = R.string.menu_settings)
        else -> filterMenuText
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
        )

        if (!isHolidaysTab) {
            FilterButton(
                isExpanded = isFilterMenuExpanded,
                onExpandedChange = onFilterMenuExpandedChange,
                onMenuItemClick = { action, text ->
                    onFilterMenuTextChange(text)
                    onAction(action)
                }
            )
        }
    }
}

@Composable
private fun FilterButton(
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onMenuItemClick: (NavigationAction, String) -> Unit
) {
    val context = LocalContext.current

    IconButton(
        modifier = Modifier,
        onClick = { onExpandedChange(true) }
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = stringResource(id = R.string.feat_calendar_filter),
            tint = MaterialTheme.colorScheme.onSurface
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.feat_calendar_filter_by_year)) },
                onClick = {
                    onExpandedChange(false)
                    onMenuItemClick(
                        NavigationAction.OnSelectedYearlyView,
                        context.getString(R.string.feat_calendar_filter_by_year)
                    )
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.feat_calendar_filter_by_month)) },
                onClick = {
                    onExpandedChange(false)
                    onMenuItemClick(
                        NavigationAction.OnSelectedMonthlyView,
                        context.getString(R.string.feat_calendar_filter_by_month)
                    )
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.feat_calendar_filter_by_week)) },
                onClick = {
                    onExpandedChange(false)
                    onMenuItemClick(
                        NavigationAction.OnSelectedWeeklyView,
                        context.getString(R.string.feat_calendar_filter_by_week)
                    )
                }
            )
        }
    }
}