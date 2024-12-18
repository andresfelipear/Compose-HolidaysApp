package com.aarevalo.holidays.screens.main

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aarevalo.holidays.R
import com.aarevalo.holidays.screens.BottomTab

@Composable
fun HomeScreenRoot(
    navigateTo: () -> Unit
){
    val viewModel : HomeScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val events = viewModel.events

    val context = LocalContext.current

    LaunchedEffect(events){
        events.collect { event ->
            when(event){
                is HomeScreenEvent.UpdatedYear -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.feat_calendar_year_updated, state.currentYear),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    HomeScreen(
        state = state,
        onAction = { action ->
            when(action){
                is HomeScreenAction.OnSelectedMonthlyView ->{
                    navigateTo()

                }
                is HomeScreenAction.OnSelectedWeeklyView ->{

                }
                else -> viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeDataState = HomeDataState(),
    onAction: (HomeScreenAction) -> Unit = {}
){
    val context = LocalContext.current
    var isFilterMenuExpanded by remember { mutableStateOf(false) }
    var filterMenuText by remember { mutableStateOf(context.getString(R.string.feat_calendar_filter_by_default)) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = stringResource(id = R.string.menu),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable {
                            println("Menu")
                        }
                    )
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ){
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = filterMenuText,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold,
                        )

                        IconButton(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            onClick = {
                                isFilterMenuExpanded = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = stringResource(id = R.string.feat_calendar_filter),
                                tint = MaterialTheme.colorScheme.onSurface
                            )

                            DropdownMenu(
                                expanded = isFilterMenuExpanded,
                                onDismissRequest = { isFilterMenuExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.feat_calendar_filter_by_year),
                                        )
                                    },
                                    onClick = {
                                        isFilterMenuExpanded = false
                                        filterMenuText = context.getString(R.string.feat_calendar_filter_by_year)
                                    }
                                )

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.feat_calendar_filter_by_month),
                                        )
                                    },
                                    onClick = {
                                        isFilterMenuExpanded = false
                                        filterMenuText = context.getString(R.string.feat_calendar_filter_by_month)
                                        onAction(HomeScreenAction.OnSelectedMonthlyView)
                                    }
                                )

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.feat_calendar_filter_by_day),
                                        )
                                    },
                                    onClick = {
                                        isFilterMenuExpanded = false
                                        filterMenuText = context.getString(R.string.feat_calendar_filter_by_day)
                                    }
                                )
                            }
                        }

                    }

                },
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .padding(0.dp)
            ) {
                MyBottomTabsBar(
                    bottomTabs = BottomTab.BOTTOM_TABS,
                    currentBottomTab = BottomTab.Main,
                    onTabClicked = {}
                )
            }
        },
        content = { paddingValues ->
            YearCalendarComponent( modifier = Modifier.padding(paddingValues))
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    HomeScreen()
}
