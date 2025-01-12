import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import com.aarevalo.holidays.screens.common.navigation.NavigationRootAction
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(modifier: Modifier = Modifier,
                onAction: (NavigationRootAction) -> Unit = {},
                isNotHomeTab: Boolean = false,
                drawerState: DrawerState) {
    var isFilterMenuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var filterMenuText by remember {
        mutableStateOf(context.getString(R.string.feat_calendar_filter_by_default))
    }

    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            MenuIcon(
                drawerState = drawerState
            )
        },
        title = {
            key(isNotHomeTab) {
                TitleContent(
                    isNotHomeTab = isNotHomeTab,
                    filterMenuText = filterMenuText,
                    isFilterMenuExpanded = isFilterMenuExpanded,
                    onFilterMenuExpandedChange = { isFilterMenuExpanded = it },
                    onFilterMenuTextChange = { filterMenuText = it },
                    onAction = onAction
                )
            }
        }
    )
}

@Composable
private fun MenuIcon(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
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
}

@Composable
private fun TitleContent(
    isNotHomeTab: Boolean,
    filterMenuText: String,
    isFilterMenuExpanded: Boolean,
    onFilterMenuExpandedChange: (Boolean) -> Unit,
    onFilterMenuTextChange: (String) -> Unit,
    onAction: (NavigationRootAction) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = if(isNotHomeTab) stringResource(id = R.string.feat_holidays_title) else filterMenuText,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
        )

        if (!isNotHomeTab) {
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
    onMenuItemClick: (NavigationRootAction, String) -> Unit
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
                        NavigationRootAction.OnSelectedYearlyView,
                        context.getString(R.string.feat_calendar_filter_by_year)
                    )
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.feat_calendar_filter_by_month)) },
                onClick = {
                    onExpandedChange(false)
                    onMenuItemClick(
                        NavigationRootAction.OnSelectedMonthlyView,
                        context.getString(R.string.feat_calendar_filter_by_month)
                    )
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.feat_calendar_filter_by_week)) },
                onClick = {
                    onExpandedChange(false)
                    onMenuItemClick(
                        NavigationRootAction.OnSelectedWeeklyView,
                        context.getString(R.string.feat_calendar_filter_by_day)
                    )
                }
            )
        }
    }
}