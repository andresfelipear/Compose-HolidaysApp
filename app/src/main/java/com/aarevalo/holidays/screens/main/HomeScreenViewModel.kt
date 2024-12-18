package com.aarevalo.holidays.screens.main

import androidx.lifecycle.ViewModel
import com.aarevalo.holidays.calendar.domain.model.HomeDataState
import com.aarevalo.holidays.calendar.domain.model.HomeScreenEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel: ViewModel() {
    private val _state = MutableStateFlow(HomeDataState())
    val state = _state.asStateFlow()

    private val _events = MutableStateFlow<HomeScreenEvent?>(null)
    val events = _events.asStateFlow()





}