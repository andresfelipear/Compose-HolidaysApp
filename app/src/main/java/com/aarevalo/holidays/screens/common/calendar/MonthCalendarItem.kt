package com.aarevalo.holidays.screens.common.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aarevalo.holidays.screens.common.calendar.components.DayContent
import com.aarevalo.holidays.screens.common.calendar.components.MonthHeader
import com.aarevalo.holidays.screens.common.calendar.components.WeekHeader
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import java.time.Month
import java.time.YearMonth

@Composable
fun MonthCalendarItem(
    month: Month,
    year: Int,
    modifier: Modifier = Modifier,
    monthlyView: Boolean = true
)
{
    val calendarState = rememberCalendarState(
        initialMonth = YearMonth.of(year, month),
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 2.dp, vertical = 0.dp)
    ){
        StaticCalendar(
            modifier = Modifier
                .fillMaxWidth(),
            calendarState = calendarState,
            daysOfWeekHeader = { WeekHeader(daysOfWeek = it) },
            dayContent = { DayContent(dayState = it) },
            monthHeader = {
                MonthHeader(
                    state = it,
                    monthlyView = monthlyView
                )
            },

        )
    }
}

@Preview(showBackground = true)
@Composable
fun MonthCalendarItemPreview()
{
    MonthCalendarItem(month = Month.DECEMBER, year = 2024)
}