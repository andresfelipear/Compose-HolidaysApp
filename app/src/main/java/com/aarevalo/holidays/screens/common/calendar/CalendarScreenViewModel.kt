package com.aarevalo.holidays.screens.common.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.boguszpawlowski.composecalendar.WeekCalendar
import io.github.boguszpawlowski.composecalendar.rememberWeekCalendarState
import io.github.boguszpawlowski.composecalendar.week.Week
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.temporal.WeekFields
import javax.inject.Inject

@SuppressLint("NewApi")
@HiltViewModel
class CalendarScreenViewModel @Inject constructor() : ViewModel(){
    private val _state = MutableStateFlow(
        CalendarScreenState(
            currentYear = LocalDate.now().year,
            currentMonth = YearMonth.now(),
            currentWeek = Week.now()))
    val state = _state.asStateFlow()

    private val eventsChannel = Channel<CalendarScreenEvent>()
    val events = eventsChannel.receiveAsFlow()

    fun onAction(action: CalendarScreenAction) {
        viewModelScope.launch {
            when(action) {
                is CalendarScreenAction.UpdateYear -> {
                    if (action.increment) {
                        _state.update {
                            it.copy(currentYear = it.currentYear + 1)
                        }
                    } else{
                        _state.update {
                            it.copy(currentYear = it.currentYear - 1)
                        }
                    }

                    if(_state.value.currentYear == LocalDate.now().year){
                        _state.update {
                            it.copy(currentMonth = YearMonth.now(), currentWeek = Week.now())
                        }
                    }
                    else{
                        _state.update {
                            it.copy(currentMonth = YearMonth.of(it.currentYear, it.currentMonth.month), currentWeek = Week.now())
                        }
                    }
                    eventsChannel.send(CalendarScreenEvent.UpdatedYear)
                }

                is CalendarScreenAction.UpdateMonth -> {

                    if (action.increment) {
                        _state.update {
                            it.copy(currentMonth = it.currentMonth.plusMonths(1))
                        }
                    } else{
                        _state.update {
                            it.copy(currentMonth = it.currentMonth.minusMonths(1))
                        }
                    }

                    _state.update {
                        it.copy(currentYear = it.currentMonth.year, currentWeek = Week.now())
                    }
                    eventsChannel.send(CalendarScreenEvent.UpdatedMonth)
                }
                else -> Unit
            }
        }
    }
}

fun getCurrentWeekDays(currentMonth: YearMonth): List<LocalDate> {
    val firstDayOfMonth = currentMonth.atDay(1)
    val currentDate = LocalDate.now()

    val weekFields = WeekFields.ISO
    val dayOfWeek = weekFields.dayOfWeek()
    val currentDay = if (currentDate.year == currentMonth.year && currentDate.monthValue == currentMonth.monthValue) {
        currentDate.dayOfMonth
    } else {
        1
    }

    val startDate = firstDayOfMonth.with(dayOfWeek, (currentDay.toLong() - 1L)).minusDays((currentDate.dayOfWeek.value.toLong() - 1L))
    return List(7) { startDate.plusDays(it.toLong()) }
}
