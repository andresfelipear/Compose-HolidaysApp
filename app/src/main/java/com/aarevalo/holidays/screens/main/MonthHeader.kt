package com.aarevalo.holidays.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.boguszpawlowski.composecalendar.header.MonthState
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthHeader(
    state: MonthState,
    modifier: Modifier = Modifier,
    monthTitleFontSize: androidx.compose.ui.unit.TextUnit = 12.sp,
    monthTitleColor: Color = Color.Black,
    arrowColor: Color = MaterialTheme.colorScheme.primary,
    arrowSize: androidx.compose.ui.unit.Dp = 20.dp,
    monthlyView: Boolean = true
){
    Row(
        modifier = if(!monthlyView){
            modifier
            .fillMaxWidth()
            .padding(8.dp) }else{
            modifier
                .fillMaxWidth()
                .padding(8.dp)
                .padding(bottom = 16.dp)
            },
        horizontalArrangement = if (monthlyView) Arrangement.SpaceBetween else Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (monthlyView) {
            IconButton(
                onClick = {
                    state.currentMonth = state.currentMonth.minusMonths(1)
                },
                modifier = Modifier.size(arrowSize)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous Month",
                    tint = arrowColor
                )
            }

        }

        Text(
            text = state.currentMonth.month.getDisplayName(TextStyle.SHORT,Locale.getDefault()),
            fontSize = monthTitleFontSize,
            color = monthTitleColor,
            fontWeight = FontWeight.W300
        )

        if (monthlyView){
            IconButton(
                onClick = { state.currentMonth = state.currentMonth.plusMonths(1)},
                modifier = Modifier.size(arrowSize)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Next Month",
                    tint = arrowColor
                )
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CustomMonthHeaderPreview(){
    MonthHeader(state = MonthState(
        initialMonth = YearMonth.now(),
        minMonth = YearMonth.of(1994, Month.JANUARY),
        maxMonth = YearMonth.of(2023, Month.DECEMBER)
    ))
}