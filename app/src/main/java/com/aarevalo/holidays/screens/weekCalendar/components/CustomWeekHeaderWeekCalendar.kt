package com.aarevalo.holidays.screens.weekCalendar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenAction
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CustomWeekHeaderWeekCalendar(
    modifier: Modifier = Modifier,
    week: Week,
    onAction: (CalendarScreenAction) -> Unit
) {

    Row(modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {

        IconButton(onClick = {
            onAction(CalendarScreenAction.UpdateWeek(increment = false))
        }, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Previous Week",
                tint = MaterialTheme.colorScheme.error)
        }

        Text(text = "${
            week.yearMonth.month.getDisplayName(TextStyle.FULL,
                Locale.getDefault())
        } ${week.yearMonth.year}", style = MaterialTheme.typography.headlineLarge)

        IconButton(onClick = {
            onAction(CalendarScreenAction.UpdateWeek(increment = true))
        }, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next Week",
                tint = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CustomWeekHeaderWeekCalendarPreview() {
    CustomWeekHeaderWeekCalendar(week = Week.now(), onAction = {})
}