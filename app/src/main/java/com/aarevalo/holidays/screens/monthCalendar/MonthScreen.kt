package com.aarevalo.holidays.screens.monthCalendar

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.aarevalo.holidays.R
import com.aarevalo.holidays.common.navigation.Route
import com.aarevalo.holidays.screens.common.calendar.MonthCalendarItem

@Composable
fun MonthScreenRoot(){
    val viewModel: MonthScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val events = viewModel.events

    val context = LocalContext.current

    LaunchedEffect(true) {
        events.collect { event ->
            when(event) {
                is MonthScreenEvent.UpdatedMonth -> {
                    Toast.makeText(context,
                        context.getString(R.string.feat_calendar_month_updated,
                            state.currentMonth),
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    MonthViewScreen(
        state = state,
        onAction = { action ->
            when(action){
                is MonthScreenAction.UpdateMonth -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

@Composable
fun MonthViewScreen(
    modifier: Modifier = Modifier,
    state: MonthScreenState = MonthScreenState(),
    onAction: (MonthScreenAction) -> Unit = {}
){
    Box(
        modifier = modifier.fillMaxSize()
    ){
        MonthCalendarItem(
            month = state.currentMonth,
            year = state.currentYear,
            monthlyView = true
        )
    }

}
