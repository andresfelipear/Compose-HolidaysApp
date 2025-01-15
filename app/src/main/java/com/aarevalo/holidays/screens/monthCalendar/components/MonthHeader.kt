package com.aarevalo.holidays.screens.monthCalendar.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenAction
import io.github.boguszpawlowski.composecalendar.header.MonthState
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MonthHeader(
    state: MonthState,
    modifier: Modifier = Modifier,
    arrowColor: Color = MaterialTheme.colorScheme.error,
    onAction: (CalendarScreenAction) -> Unit,
) {
    Row(modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {

        IconButton(onClick = {
            onAction(CalendarScreenAction.UpdateMonth(increment = false))
        }, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Previous Month",
                tint = arrowColor)
        }

        Text(text = "${
            state.currentMonth.month.getDisplayName(TextStyle.FULL,
                Locale.getDefault())
        } ${state.currentMonth.year}", style = MaterialTheme.typography.headlineLarge)

        IconButton(onClick = {
            onAction(CalendarScreenAction.UpdateMonth(increment = true))
        }, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next Month",
                tint = arrowColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomMonthHeaderPreview() {
    MonthHeader(state = MonthState(initialMonth = YearMonth.now(),
        minMonth = YearMonth.of(1994, Month.JANUARY),
        maxMonth = YearMonth.of(2023, Month.DECEMBER)), onAction = {})
}