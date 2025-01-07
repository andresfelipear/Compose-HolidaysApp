package com.aarevalo.holidays.screens.yearCalendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenAction
import java.time.Month

@Composable
fun YearCalendarComponent(
    currentYear: Int,
    modifier: Modifier = Modifier,
    onAction: (CalendarScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        YearNavigationRow(
            currentYear = currentYear,
            onAction = onAction
        )

        YearMonthsGrid(
            currentYear = currentYear
        )
    }
}

@Composable
fun YearNavigationRow(
    currentYear: Int,
    onAction: (CalendarScreenAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(30.dp),
            onClick = {
                onAction(CalendarScreenAction.UpdateYear(increment = false))
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Previous Year",
                tint = MaterialTheme.colorScheme.error
            )
        }

        Text(
            text = currentYear.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        IconButton(
            modifier = Modifier.size(30.dp),
            onClick = {
                onAction(CalendarScreenAction.UpdateYear(increment = true))
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next Year",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun YearMonthsGrid(currentYear: Int) {
    val monthIndices = remember { Month.entries.toTypedArray() }

    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        for (row in 0 until 4) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                for (col in 0 until 3) {
                    val monthIndex = row * 3 + col
                    if (monthIndex < monthIndices.size) {
                        CustomMonthCalendar(
                            year = currentYear,
                            month = monthIndices[monthIndex],
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CalendarPreview(){
    YearCalendarComponent(
        currentYear = 2023,
        onAction = {}
    )
}