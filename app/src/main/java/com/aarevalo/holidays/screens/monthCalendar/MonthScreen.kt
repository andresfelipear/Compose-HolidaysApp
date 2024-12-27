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
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenAction
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenEvent
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenState
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel
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
                            state.currentMonth),
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
        )
//        MonthCalendarItem(
//            month = state.currentMonth,
//            year = state.currentYear,
//            monthlyView = true
//        )
    }

}
