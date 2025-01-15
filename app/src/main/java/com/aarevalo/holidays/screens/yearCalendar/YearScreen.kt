package com.aarevalo.holidays.screens.yearCalendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aarevalo.holidays.domain.model.Country
import com.aarevalo.holidays.domain.model.Holiday
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenAction
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenState
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.YearMonth

@Composable
fun YearScreenRoot(
    viewModel: CalendarScreenViewModel
)
{
    val state by viewModel.state.collectAsState()
    val holidays by viewModel.holidays.collectAsState()

    LaunchedEffect(state.currentYear) {
        viewModel.fetchHolidays()
    }

    YearScreen(
        state = state,
        onAction = { action ->
            when(action){
                is CalendarScreenAction.UpdateYear -> {
                    viewModel.onAction(action)
                }
            }
        },
        holidays = holidays
    )
}

@Composable
fun YearScreen(
    modifier: Modifier = Modifier,
    state: CalendarScreenState,
    onAction: (CalendarScreenAction) -> Unit = {},
    holidays: List<Holiday> = emptyList()
){
    val currentYearHolidays by remember(state.currentYear, holidays) {
        mutableStateOf(
            holidays.filter { it.date.year == state.currentYear }
        )
    }
    YearCalendarComponent(
        modifier = modifier,
        currentYear = state.currentYear,
        onAction = onAction,
        holidays = currentYearHolidays
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    YearScreen(
        state = CalendarScreenState(
            currentYear = 2023,
            currentMonth = YearMonth.now(),
            currentWeek = Week.now(),
            country = Country("Canada", "CA")))
}
