package com.aarevalo.holidays.screens.yearCalendar

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.aarevalo.holidays.R
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenAction
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenEvent
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenState
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel
import java.time.YearMonth

@Composable
fun YearScreenRoot(
    viewModel: CalendarScreenViewModel
)
{
    val state by viewModel.state.collectAsState()
    val events = viewModel.events

    val context = LocalContext.current

    LaunchedEffect(events){
        events.collect { event ->
            when(event){
                is CalendarScreenEvent.UpdatedYear -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.feat_calendar_year_updated, state.currentYear),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> Unit
            }
        }
    }

    YearScreen(
        state = state,
        onAction = { action ->
            when(action){
                is CalendarScreenAction.UpdateYear -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

@Composable
fun YearScreen(
    modifier: Modifier = Modifier,
    state: CalendarScreenState,
    onAction: (CalendarScreenAction) -> Unit = {}
){
    YearCalendarComponent(
        modifier = modifier,
        currentYear = state.currentYear,
        onAction = onAction
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    YearScreen(state = CalendarScreenState(currentYear = 2023, currentMonth = YearMonth.now()))
}
