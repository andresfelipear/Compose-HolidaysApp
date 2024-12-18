package com.aarevalo.holidays.screens.common.calendar.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import java.time.DayOfWeek
import java.util.Locale
import java.time.format.TextStyle.NARROW

@SuppressLint("NewApi")
@Composable
fun WeekHeader(
    daysOfWeek: List<DayOfWeek>,
){
    Row{
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(NARROW, Locale.ROOT),
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                style = MaterialTheme.typography.bodySmall,
                color = if(dayOfWeek == DayOfWeek.SUNDAY) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            )
        }
    }
}