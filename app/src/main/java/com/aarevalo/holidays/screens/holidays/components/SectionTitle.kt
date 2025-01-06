package com.aarevalo.holidays.screens.holidays.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .background( color = MaterialTheme.colorScheme.surfaceContainer)
            .padding(8.dp)
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SectionTitlePreview(){
    SectionTitle(title = "Section Title")
}