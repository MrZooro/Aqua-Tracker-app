package com.example.aquatracker.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HotWaterScreen(paddingValues: PaddingValues) {

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = paddingValues) {  }
    Text(text = "HotWaterScreen")
}