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

@Composable
fun YearScreenRoot()
{
    val viewModel : YearScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val events = viewModel.events

    val context = LocalContext.current

    LaunchedEffect(events){
        events.collect { event ->
            when(event){
                is YearScreenEvent.UpdatedYear -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.feat_calendar_year_updated, state.currentYear),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    YearScreen(
        state = state,
        onAction = { action ->
            when(action){
                is YearScreenAction.UpdateYear -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

@Composable
fun YearScreen(
    modifier: Modifier = Modifier,
    state: YearScreenState = YearScreenState(),
    onAction: (YearScreenAction) -> Unit = {}
){
    YearCalendarComponent(
        currentYear = state.currentYear,
        onAction = onAction
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    YearScreen()
}
