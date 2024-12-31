package com.aarevalo.holidays.screens.weekCalendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenAction
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenState
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel
import com.aarevalo.holidays.screens.weekCalendar.components.WeekHeaderWeekCalendar
import io.github.boguszpawlowski.composecalendar.WeekCalendar
import io.github.boguszpawlowski.composecalendar.rememberWeekCalendarState
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.YearMonth

@Composable
fun WeeklyCalendarScreenRoot(
   viewModel: CalendarScreenViewModel
){
    val state by viewModel.state.collectAsState()
    val events = viewModel.events

    WeeklyCalendarScreen(
        state = state,
        onAction = { action ->
            when(action){
                is CalendarScreenAction.UpdateWeek -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

@Composable
fun WeeklyCalendarScreen(
    modifier: Modifier = Modifier,
    state: CalendarScreenState,
    onAction: (CalendarScreenAction) -> Unit = {}
){
    Box(
        modifier = modifier.fillMaxSize()
    ){
        val calendarState = rememberWeekCalendarState()
        calendarState.weekState.currentWeek = state.currentWeek
        WeekCalendar(
            calendarState = calendarState,
            weekHeader = { WeekHeaderWeekCalendar(state = it, onAction = onAction) },
        )
    }
}

@Composable
@Preview(showBackground = true)
fun WeeklyCalendarScreenPreview() {
    WeeklyCalendarScreen(
        state = CalendarScreenState(
            currentYear = 2024,
            currentMonth = YearMonth.now(),
            currentWeek = Week.now()))
}