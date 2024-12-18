package com.aarevalo.holidays.screens.common.calendar.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import io.github.boguszpawlowski.composecalendar.day.NonSelectableDayState
import java.time.DayOfWeek

@SuppressLint("NewApi")
@Composable
fun DayContent(dayState: NonSelectableDayState){
    Text(
        text = dayState.date.dayOfMonth.toString(),
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelSmall,
        color = if(dayState.date.dayOfWeek == DayOfWeek.SUNDAY) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
        fontSize = 10.sp
    )
}
