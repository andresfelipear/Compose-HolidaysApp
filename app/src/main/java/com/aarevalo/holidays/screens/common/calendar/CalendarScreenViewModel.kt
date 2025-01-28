package com.aarevalo.holidays.screens.common.calendar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarevalo.holidays.R
import com.aarevalo.holidays.domain.HolidayCache
import com.aarevalo.holidays.domain.model.Country
import com.aarevalo.holidays.domain.model.DrawerItem
import com.aarevalo.holidays.domain.model.Holiday
import com.aarevalo.holidays.domain.model.State
import com.aarevalo.holidays.domain.model.WeekDateGenerator
import com.aarevalo.holidays.domain.usecases.HolidayUseCase
import com.aarevalo.holidays.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.boguszpawlowski.composecalendar.week.Week
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarScreenViewModel @Inject constructor(
    private val holidayUseCase: HolidayUseCase,
    private val holidaysCache: HolidayCache,
) : ViewModel(){
    private val _state = MutableStateFlow(
        CalendarScreenState(
            currentYear = LocalDate.now().year,
            currentMonth = YearMonth.now(),
            currentWeek = Week.now(),
            country = Country(
                name = "Canada",
                code = "CA"
            ),
            state = State(
                name = "British Columbia",
                code = "BC"
            )
        )
    )
    val state = _state.asStateFlow()

    private val eventsChannel = Channel<CalendarScreenEvent>()

    val holidays = MutableStateFlow<List<Holiday>>(emptyList())
    val countries = MutableStateFlow<List<Country>>(emptyList())
    val drawerItems = MutableStateFlow<List<DrawerItem>>(emptyList())

    init {
        viewModelScope.launch {
            getCurrentLocation()
            fetchHolidays()
            fetchCountries()
        }

        drawerItems.value = listOf(
            DrawerItem(
                route = Route.Settings,
                icon = Icons.Rounded.Settings,
                title = R.string.menu_settings
            ),
            DrawerItem(
                route = Route.About,
                icon = Icons.Rounded.Info,
                title = R.string.menu_about
            )
        )
    }

    suspend fun getCurrentLocation() {
        withContext(Dispatchers.IO){
            holidayUseCase.getCurrentLocationUseCase().apply {
                _state.update {
                    it.copy(country = first, state = second)
                }
            }
        }
    }


    suspend fun fetchHolidays(){
        withContext(Dispatchers.IO){
            state.value.country?.let{ country ->
                holidays.value = holidayUseCase.fetchHolidaysUseCase(state.value.currentYear, country, state.value.state)
            }
        }
    }

    private suspend fun fetchCountries(){
        withContext(Dispatchers.IO){
            countries.value = holidayUseCase.fetchListOfCountriesUseCase()
        }
    }

    fun onAction(action: CalendarScreenAction) {
        viewModelScope.launch {
            when(action) {
                is CalendarScreenAction.UpdateYear -> {
                    holidaysCache.clearHolidays()

                    _state.update {
                        val newCurrentYear = if(action.increment) it.currentYear + 1 else it.currentYear - 1
                        val newCurrentMonth : YearMonth
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

                        if(newCurrentYear != it.currentYear){
                            holidaysCache.clearHolidays()
                            it.copy(currentYear = newCurrentYear, currentMonth = newCurrentMonth, currentWeek = newCurrentWeek)
                        } else {
                            it.copy(currentMonth = newCurrentMonth, currentWeek = newCurrentWeek)
                        }
                    }
                    eventsChannel.send(CalendarScreenEvent.UpdatedMonth)
                }

                is CalendarScreenAction.UpdateWeek -> {
                    _state.update {
                        val newCurrentWeek = if(action.increment) it.currentWeek.plusWeeks(1) else it.currentWeek.minusWeeks(1)
                        val newCurrentMonth = newCurrentWeek.yearMonth
                        val newCurrentYear = newCurrentMonth.year

                        if(newCurrentYear != it.currentYear){
                            holidaysCache.clearHolidays()
                            it.copy(currentYear = newCurrentYear, currentMonth = newCurrentMonth, currentWeek = newCurrentWeek)
                        } else {
                            it.copy(currentMonth = newCurrentMonth, currentWeek = newCurrentWeek)
                        }
                    }
                }

                is CalendarScreenAction.UpdateCountry -> {
                    _state.update {
                        it.copy(country = action.country)
                    }
                    holidaysCache.clearHolidays()
                }

                else -> Unit
            }
        }
    }
}
