package com.aarevalo.holidays.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {
    private val _state = MutableStateFlow(HomeDataState())
    val state = _state.asStateFlow()

    private val eventsChannel = Channel<HomeScreenEvent>()
    val events = eventsChannel.receiveAsFlow()

    fun onAction(action: HomeScreenAction) {
        viewModelScope.launch {
            when(action) {
                is HomeScreenAction.UpdateYear -> {
                    if (action.increment) {
                        _state.update {
                            it.copy(currentYear = it.currentYear + 1)
                        }
                    } else{
                        _state.update {
                            it.copy(currentYear = it.currentYear - 1)
                        }
                    }
                    eventsChannel.send(HomeScreenEvent.UpdatedYear)
                }
                else -> Unit
            }
        }
    }
}