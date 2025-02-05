package com.aarevalo.holidays.screens.holidays

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aarevalo.holidays.data.local.FakeHolidaysLocalDataSource.holidays
import com.aarevalo.holidays.domain.model.Country
import com.aarevalo.holidays.domain.model.Holiday
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenState
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel
import com.aarevalo.holidays.screens.holidays.components.HolidayItem
import com.aarevalo.holidays.screens.holidays.components.SectionTitle
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.YearMonth
import java.util.Locale

@Composable
fun HolidaysScreenRoot(
    viewModel: CalendarScreenViewModel
){
    val holidays by viewModel.holidays.collectAsState()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchHolidays()
    }

    HolidaysScreen(
        holidays = holidays,
        state = state
    )
}

@Composable
fun HolidaysScreen(
    modifier: Modifier = Modifier,
    holidays: List<Holiday>,
    state: CalendarScreenState
){
    val groupedHolidays = holidays
        .filter { holiday -> holiday.date.year ==  state.currentYear}
        .groupBy { it.date.month }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 0.dp),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        groupedHolidays.forEach { (month, holidays) ->
            item {
                SectionTitle(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface
                        )
                        .fillParentMaxWidth(),
                    title = month.toString().lowercase().replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }
                )
            }
            items(
                items = holidays,
                key = { holiday -> "${holiday.date}-${holiday.name}"}
            ){ holiday ->
                HolidayItem(holiday = holiday)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HolidaysScreenPreview(){
    HolidaysScreen(holidays = holidays, state = CalendarScreenState(
        currentYear = 2024,
        currentMonth = YearMonth.now(),
        currentWeek = Week.now(),
        country = Country("Canada", "CA")
    ))
}