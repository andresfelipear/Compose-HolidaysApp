package com.aarevalo.holidays.screens.yearCalendar

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
class YearScreenViewModel @Inject constructor() : ViewModel(){
    private val _state = MutableStateFlow(YearScreenState())
    val state = _state.asStateFlow()

    private val eventsChannel = Channel<YearScreenEvent>()
    val events = eventsChannel.receiveAsFlow()

    fun onAction(action: YearScreenAction) {
        viewModelScope.launch {
            when(action) {
                is YearScreenAction.UpdateYear -> {
                    if (action.increment) {
                        _state.update {
                            it.copy(currentYear = it.currentYear + 1)
                        }
                    } else{
                        _state.update {
                            it.copy(currentYear = it.currentYear - 1)
                        }
                    }
                    eventsChannel.send(YearScreenEvent.UpdatedYear)
                }
                else -> Unit
            }
        }
    }
}