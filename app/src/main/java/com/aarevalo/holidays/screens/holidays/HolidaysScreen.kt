package com.aarevalo.holidays.screens.holidays

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HolidaysScreenRoot(
    navigateBack: () -> Unit
){
    HolidaysScreen()
}

@Composable
fun HolidaysScreen(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.fillMaxSize()
    ){
        Text(text = "Holidays")
    }
}