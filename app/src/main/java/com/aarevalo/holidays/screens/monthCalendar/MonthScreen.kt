package com.aarevalo.holidays.screens.monthCalendar

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aarevalo.holidays.R
import com.aarevalo.holidays.data.local.FakeHolidaysLocalDataSource.holidays
import com.aarevalo.holidays.domain.model.Country
import com.aarevalo.holidays.domain.model.Holiday
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenAction
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenEvent
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenState
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel
import com.aarevalo.holidays.screens.common.calendar.components.DayContent
import com.aarevalo.holidays.screens.common.calendar.components.MonthHeader
import com.aarevalo.holidays.screens.common.calendar.components.WeekHeader
import com.aarevalo.holidays.screens.holidays.components.HolidayItem
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.YearMonth

@Composable
fun MonthScreenRoot(viewModel: CalendarScreenViewModel){
    val state by viewModel.state.collectAsState()
    val events = viewModel.events
    val holidays by viewModel.holidays.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchHolidays()
    }

    val context = LocalContext.current

    LaunchedEffect(true) {
        events.collect { event ->
            when(event) {
                is CalendarScreenEvent.UpdatedMonth -> {
                    Toast.makeText(context,
                        context.getString(R.string.feat_calendar_month_updated,
                            state.currentMonth.month.toString()),
                        Toast.LENGTH_SHORT)
                        .show()
                }
                else -> Unit
            }
        }
    }

    MonthViewScreen(
        state = state,
        onAction = { action ->
            when(action){
                is CalendarScreenAction.UpdateMonth -> {
                    viewModel.onAction(action)
                }
            }
        },
        holidays = holidays
    )
}

@Composable
fun MonthViewScreen(
    modifier: Modifier = Modifier,
    state: CalendarScreenState,
    onAction: (CalendarScreenAction) -> Unit = {},
    holidays: List<Holiday>
){
    val monthHolidays = holidays.filter { it.date.month == state.currentMonth.month }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ){
        item{
            val calendarState = rememberCalendarState()

            calendarState.monthState.currentMonth = state.currentMonth

            StaticCalendar(
                modifier = Modifier
                    .fillMaxSize(),
                calendarState = calendarState,
                monthHeader = { MonthHeader(state = it, onAction = onAction) },
                daysOfWeekHeader = { WeekHeader(daysOfWeek = it, ) },
                dayContent = { DayContent(state = it, holidays = monthHolidays) },
            )
        }
        items(
            items = monthHolidays,
            key = { holiday -> "${holiday.date}-${holiday.name}"}
        ){ holiday ->
            HolidayItem(holiday = holiday)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MonthViewScreenPreview(){
    MonthViewScreen(
        state = CalendarScreenState(
            currentYear = 2024,
            currentMonth = YearMonth.now(),
            currentWeek = Week.now(),
            country = Country("Canada", "CA")
        ),
        holidays = holidays
    )
}