package com.aarevalo.holidays.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import java.time.Month
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthCalendarItem(
    month: Month,
    year: Int,
    modifier: Modifier = Modifier,
    monthlyView: Boolean = true
)
{
    val monthStartDate = YearMonth.of(year, month)
    val calendarState = rememberCalendarState(
        initialMonth = monthStartDate,
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(4.dp)
    ){
        StaticCalendar(
            modifier = Modifier
                .aspectRatio(0.7f)
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


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MonthCalendarItemPreview()
{
    MonthCalendarItem(month = Month.DECEMBER, year = 2024)
}