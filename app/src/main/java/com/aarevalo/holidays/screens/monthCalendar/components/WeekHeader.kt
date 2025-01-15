package com.aarevalo.holidays.screens.monthCalendar.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import java.time.DayOfWeek
import java.util.Locale
import java.time.format.TextStyle.SHORT

@Composable
fun WeekHeader(
    daysOfWeek: List<DayOfWeek>,
){
    Row{
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(SHORT, Locale.ROOT),
                style = MaterialTheme.typography.titleMedium,
                color = if(dayOfWeek == DayOfWeek.SUNDAY) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            )
        }
    }
}