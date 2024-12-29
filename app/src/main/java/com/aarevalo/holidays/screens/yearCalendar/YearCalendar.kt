package com.aarevalo.holidays.screens.yearCalendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun YearCalendar(
    currentYear: Int,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        for(row in 0..3){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ){
                for(col in 0..2){
                    val monthIndex = row * 3 + col
                    if(monthIndex < 12){
                        SimpleMonthCalendar(
                            year = currentYear,
                            month = Month.of(monthIndex + 1),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleMonthCalendar(
    year: Int,
    month: Month,
    modifier: Modifier = Modifier
){
    val yearMonth = YearMonth.of(year, month)
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value % 7

    val prevMonth = yearMonth.minusMonths(1)
    val nextMonth = yearMonth.plusMonths(1)
    val prevMonthDays = prevMonth.lengthOfMonth()

    Column(
        modifier = modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = month.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row(modifier = Modifier.fillMaxWidth()){
            val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        for(week in 0 until 6){
            Row(modifier = Modifier.fillMaxWidth()){
                for(dayOfWeek in 0 until 7) {
                    val day = week * 7 + dayOfWeek - firstDayOfWeek + 1
                    val currentDay = when {
                        day < 1 -> {
                            prevMonth.atDay(prevMonthDays + day)
                        }
                        day in 1..daysInMonth -> {
                            yearMonth.atDay(day)
                        }
                        else -> {
                            nextMonth.atDay(day - daysInMonth)
                        }
                    }
                    Box(
                        modifier = Modifier.weight(1f).aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ){
                        val dayText = currentDay.dayOfMonth.toString()
                        val dayColor = when {
                            day in 1..daysInMonth ->{
                                if(currentDay.dayOfWeek == DayOfWeek.SUNDAY){
                                    MaterialTheme.colorScheme.primary
                                }else{
                                    MaterialTheme.colorScheme.onSurface
                                }
                            }
                            else -> {
                                if(currentDay.dayOfWeek == DayOfWeek.SUNDAY){
                                    MaterialTheme.colorScheme.primary
                                }else{
                                    MaterialTheme.colorScheme.secondary
                                }
                            }
                        }
                        Text(
                            text = dayText,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = dayColor
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YearCalendarPreview(){
    YearCalendar(currentYear = 2024)
}