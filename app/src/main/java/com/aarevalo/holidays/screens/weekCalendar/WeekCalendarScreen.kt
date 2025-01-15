package com.aarevalo.holidays.screens.weekCalendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aarevalo.holidays.domain.model.Country
import com.aarevalo.holidays.domain.model.Holiday
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenAction
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenState
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel
import com.aarevalo.holidays.screens.holidays.components.HolidayItem
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
    val holidays by viewModel.holidays.collectAsState()

    LaunchedEffect(state.currentYear) {
        viewModel.fetchHolidays()
    }

    WeeklyCalendarScreen(
        state = state,
        onAction = { action ->
            when(action){
                is CalendarScreenAction.UpdateWeek -> {
                    viewModel.onAction(action)
                }
            }
        },
        holidays = holidays
    )
}

@Composable
fun WeeklyCalendarScreen(
    modifier: Modifier = Modifier,
    state: CalendarScreenState,
    onAction: (CalendarScreenAction) -> Unit = {},
    holidays: List<Holiday>
){
    val weekHolidays by remember(state.currentWeek, holidays) {
        mutableStateOf(holidays.filter { it.date.month == state.currentMonth.month || it.date.month == state.currentMonth.plusMonths(1).month || it.date.month == state.currentMonth.minusMonths(1).month}.filter{
            it.date in state.currentWeek.start .. state.currentWeek.end
        })
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ){
        item{
            CustomWeekCalendar(
                week = state.currentWeek,
                onAction = onAction,
                holidays = holidays
            )
        }
        items(
            items = weekHolidays,
            key = { holiday -> "${holiday.date}-${holiday.name}"}
        ){ holiday ->
            HolidayItem(holiday = holiday)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WeeklyCalendarScreenPreview() {
    WeeklyCalendarScreen(
        state = CalendarScreenState(
            currentYear = 2024,
            currentMonth = YearMonth.now(),
            currentWeek = Week.now(),
            country = Country("Canada", "CA")),
        holidays = emptyList())
}