package com.aarevalo.holidays.screens.common.calendar.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import com.aarevalo.holidays.domain.model.Holiday
import io.github.boguszpawlowski.composecalendar.day.NonSelectableDayState
import java.time.DayOfWeek

@SuppressLint("NewApi")
@Composable
fun DayContent(
    state: NonSelectableDayState,
    modifier: Modifier = Modifier,
    holidays: List<Holiday>
){
    val date = state.date
    val selectionState = state.selectionState
    val isSelected = selectionState.isDateSelected(date)

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(if (state.isFromCurrentMonth) 8.dp else 1.dp),
        border = if (state.isCurrentDay) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        if(state.isFromCurrentMonth && date.dayOfMonth.toString() in holidays.map { it.date.dayOfMonth.toString() })
        {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(6.dp))
                    .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = date.dayOfMonth.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = date.dayOfMonth.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = if(date.dayOfWeek == DayOfWeek.SUNDAY){
                        MaterialTheme.colorScheme.error
                    } else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
