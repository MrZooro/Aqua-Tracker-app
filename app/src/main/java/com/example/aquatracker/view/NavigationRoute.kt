package com.example.aquatracker.view

import androidx.annotation.StringRes
import com.example.aquatracker.R

enum class NavigationRoute(val route: String, @StringRes val label: Int) {
    HotWaterRoute("hotWaterRoute", R.string.hot_water_screen_title),
    ColdWaterRoute("coldWaterRoute", R.string.cold_water_screen_title)
}