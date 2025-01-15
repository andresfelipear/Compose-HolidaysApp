package com.aarevalo.holidays.screens.weekCalendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aarevalo.holidays.domain.model.Holiday
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenAction
import com.aarevalo.holidays.screens.weekCalendar.components.CustomWeekHeaderWeekCalendar
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CustomWeekCalendar(
    week: Week,
    modifier: Modifier = Modifier,
    holidays: List<Holiday>,
    onAction: (CalendarScreenAction) -> Unit
) {
    val daysOfWeek = listOf(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)
    val weekHolidays = holidays.filter { it.date in week.start..week.end }

    Column(
        modifier = modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomWeekHeaderWeekCalendar(
            week = week,
            onAction = onAction
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            daysOfWeek.forEachIndexed { index, day ->
                Text(
                    text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    color = if (index == 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            week.days.forEach { currentDay ->
                val isCurrentDay = currentDay == LocalDate.now()
                val isHoliday = currentDay.dayOfMonth in weekHolidays.map { it.date.dayOfMonth }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(2.dp),
                    shape = RoundedCornerShape(6.dp),
                    elevation = CardDefaults.cardElevation(1.dp),
                    border = if (isCurrentDay) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    )
                ) {
                    if (isHoliday) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(6.dp))
                                .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = currentDay.dayOfMonth.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = currentDay.dayOfMonth.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (currentDay.dayOfWeek == DayOfWeek.SUNDAY) {
                                    MaterialTheme.colorScheme.error
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                }
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
fun CustomWeekCalendarPreview() {
    val week = Week(
        days = listOf(
            LocalDate.of(2025, 1, 12),
            LocalDate.of(2025, 1, 13),
            LocalDate.of(2025, 1, 14),
            LocalDate.of(2025, 1, 15),
            LocalDate.of(2025, 1, 16),
            LocalDate.of(2025, 1, 17),
            LocalDate.of(2025, 1, 18)
        )
    )

    val holidays = listOf(
        Holiday("New Year", LocalDate.of(2025, 1, 1)),
        Holiday("Valentine's Day", LocalDate.of(2025, 1, 16)),
    )

    CustomWeekCalendar(week = week, holidays = holidays, onAction = { })
}
