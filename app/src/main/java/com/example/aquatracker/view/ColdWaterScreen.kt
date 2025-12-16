package com.example.aquatracker.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ColdWaterScreen(paddingValues: PaddingValues) {
    Text(modifier = Modifier.padding(paddingValues), text = "ColdWaterScreen")
}