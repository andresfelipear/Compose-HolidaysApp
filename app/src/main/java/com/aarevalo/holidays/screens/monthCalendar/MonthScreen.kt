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
import androidx.compose.ui.tooling.preview.Preview
import com.aarevalo.holidays.R
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenAction
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenEvent
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenState
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel
import com.aarevalo.holidays.screens.common.calendar.components.DayContent
import com.aarevalo.holidays.screens.common.calendar.components.MonthHeader
import com.aarevalo.holidays.screens.common.calendar.components.WeekHeader
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import java.time.YearMonth

@Composable
fun MonthScreenRoot(viewModel: CalendarScreenViewModel){
    val state by viewModel.state.collectAsState()
    val events = viewModel.events

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
        }
    )
}

@Composable
fun MonthViewScreen(
    modifier: Modifier = Modifier,
    state: CalendarScreenState,
    onAction: (CalendarScreenAction) -> Unit = {}
){
    Box(
        modifier = modifier.fillMaxSize()
    ){
        val calendarState = rememberCalendarState()

        calendarState.monthState.currentMonth = YearMonth.of(state.currentYear, state.currentMonth.month)

        StaticCalendar(
            modifier = Modifier
                .fillMaxSize(),
            calendarState = calendarState,
            monthHeader = { MonthHeader(state = it, onAction = onAction) },
            daysOfWeekHeader = { WeekHeader(daysOfWeek = it, ) },
            dayContent = { DayContent(state = it) },
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MonthViewScreenPreview(){
    MonthViewScreen(
        state = CalendarScreenState(
            currentYear = 2024,
            currentMonth = YearMonth.now()
        )
    )
}