package com.aarevalo.holidays.screens.monthCalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthScreenViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MonthScreenState())
    val state = _state.asStateFlow()

    private val eventsChannel = Channel<MonthScreenEvent>()
    val events = eventsChannel.receiveAsFlow()

    fun onAction(action: MonthScreenAction) {
        viewModelScope.launch {
            when(action) {
                is MonthScreenAction.UpdateMonth -> {
                    if (action.increment) {
                        _state.update {
                            it.copy(currentMonth = it.currentMonth.plus(1))
                        }
                    } else{
                        _state.update {
                            it.copy(currentMonth = it.currentMonth.minus(1))
                        }
                    }
                    eventsChannel.send(MonthScreenEvent.UpdatedMonth)
                }
            }
        }
    }
}
