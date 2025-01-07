package com.aarevalo.holidays.screens.yearCalendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aarevalo.holidays.data.local.FakeHolidaysLocalDataSource.holidays
import com.aarevalo.holidays.domain.model.Holiday
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CustomMonthCalendar(year: Int,
                        month: Month,
                        modifier: Modifier = Modifier,
                        holidays: List<Holiday>){
    val yearMonth = YearMonth.of(year, month)
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value % 7

    val prevMonth = yearMonth.minusMonths(1)
    val nextMonth = yearMonth.plusMonths(1)
    val prevMonthDays = prevMonth.lengthOfMonth()

    val monthHolidays = holidays.filter { it.date.month == month }

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
            daysOfWeek.forEachIndexed { index, day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    color = if(index == 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
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

                    if(currentDay == LocalDate.now()){
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary)),
                            contentAlignment = Alignment.Center
                        ){
                            val dayText = currentDay.dayOfMonth.toString()
                            Text(
                                text = dayText,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    else if(day in monthHolidays.map { it.date.dayOfMonth }){
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ){
                            val dayText = currentDay.dayOfMonth.toString()
                            Text(
                                text = dayText,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }else {
                        Box(
                            modifier = Modifier.weight(1f).aspectRatio(1f),
                            contentAlignment = Alignment.Center
                        ){
                            val dayText = currentDay.dayOfMonth.toString()
                            val dayColor = when {
                                day in 1..daysInMonth ->{
                                    if(currentDay.dayOfWeek == DayOfWeek.SUNDAY){
                                        MaterialTheme.colorScheme.error
                                    }else{
                                        MaterialTheme.colorScheme.onSurface
                                    }
                                }
                                else -> {
                                    if(currentDay.dayOfWeek == DayOfWeek.SUNDAY){
                                        MaterialTheme.colorScheme.errorContainer
                                    }else{
                                        MaterialTheme.colorScheme.outlineVariant
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
}

@Preview(showBackground = true)
@Composable
fun CustomMonthCalendarPreview(){
    CustomMonthCalendar(year = 2023, month = Month.JANUARY, holidays = holidays)
}