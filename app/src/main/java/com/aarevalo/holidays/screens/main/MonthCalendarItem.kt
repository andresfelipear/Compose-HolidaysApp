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
import androidx.compose.ui.unit.sp
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import java.time.Month
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthCalendarItem(month: Month, year: Int)
{
    val monthStartDate = YearMonth.of(year, month)
    val calendarState = rememberCalendarState(
        initialMonth = monthStartDate,
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ){
        StaticCalendar(
            monthHeader = { state ->
                CustomMonthHeader(
                    state = state,
                    monthTitleFontSize = 16.sp,
                    arrowSize = 20.dp
                )
            },
            modifier = Modifier.aspectRatio(1f),
            calendarState = calendarState,
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