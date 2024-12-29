package com.aarevalo.holidays.screens.common.calendar

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import javax.inject.Inject

@SuppressLint("NewApi")
@HiltViewModel
class CalendarScreenViewModel @Inject constructor() : ViewModel(){
    private val _state = MutableStateFlow(CalendarScreenState(currentYear = LocalDate.now().year, currentMonth = YearMonth.now()))
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
                    eventsChannel.send(CalendarScreenEvent.UpdatedYear)
                }

                is CalendarScreenAction.UpdateMonth -> {
                    if (action.increment) {
                        if(_state.value.currentMonth.monthValue == Month.DECEMBER.value){
                            _state.update {
                             it.copy(currentMonth = it.currentMonth.plusMonths(1), currentYear = it.currentYear + 1)
                            }
                        }else{
                            _state.update {
                                it.copy(currentMonth = it.currentMonth.plusMonths(1))
                            }
                        }
                    } else{
                        if(_state.value.currentMonth.monthValue == Month.JANUARY.value){
                            _state.update {
                                it.copy(currentMonth = it.currentMonth.minusMonths(1), currentYear = it.currentYear - 1)
                            }
                        }
                        else{
                            _state.update {
                                it.copy(currentMonth = it.currentMonth.minusMonths(1))
                            }
                        }
                    }
                    eventsChannel.send(CalendarScreenEvent.UpdatedMonth)
                }
                else -> Unit
            }
        }
    }
}