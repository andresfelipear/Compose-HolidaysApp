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
import java.util.Locale
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
                    _state.update {
                        val newCurrentYear = if(action.increment) it.currentYear + 1 else it.currentYear - 1
                        val newCurrentMonth : YearMonth;
                        val newCurrentWeek : Week
                        if(newCurrentYear == LocalDate.now().year){
                            newCurrentMonth = YearMonth.now()
                            newCurrentWeek = Week.now()
                        }
                        else{
                            newCurrentMonth = YearMonth.of(newCurrentYear, it.currentMonth.month)
                            newCurrentWeek = Week(WeekDateGenerator.getCurrentWeekDates(newCurrentMonth))
                        }
                        it.copy(currentYear = newCurrentYear, currentMonth = newCurrentMonth, currentWeek = newCurrentWeek)
                    }
                    eventsChannel.send(CalendarScreenEvent.UpdatedYear)
                }

                is CalendarScreenAction.UpdateMonth -> {

                    _state.update {
                        val newCurrentMonth = if(action.increment) it.currentMonth.plusMonths(1) else it.currentMonth.minusMonths(1)
                        val newCurrentYear = newCurrentMonth.year
                        val newCurrentWeek = Week(WeekDateGenerator.getCurrentWeekDates(newCurrentMonth))

                        it.copy(currentYear = newCurrentYear, currentMonth = newCurrentMonth, currentWeek = newCurrentWeek)
                    }
                    eventsChannel.send(CalendarScreenEvent.UpdatedMonth)
                }
                else -> Unit
            }
        }
    }
}
object WeekDateGenerator {
    fun generateWeekDates(yearMonth: YearMonth, weekNumber: Int): List<LocalDate> {
        val firstDayOfMonth = yearMonth.atDay(1)
        val weekFields = WeekFields.of(Locale.getDefault())

        val firstDayOfWeek = firstDayOfMonth
            .with(weekFields.weekOfMonth(), weekNumber.toLong())
            .with(weekFields.dayOfWeek(), 1)

        return (0..6).map { firstDayOfWeek.plusDays(it.toLong()) }
    }

    fun getCurrentWeekDates(yearMonth: YearMonth): List<LocalDate> {
        val today = LocalDate.now()
        val weekFields = WeekFields.of(Locale.getDefault())
        val currentWeek = today.get(weekFields.weekOfMonth())
        return generateWeekDates(yearMonth, currentWeek)
    }
}