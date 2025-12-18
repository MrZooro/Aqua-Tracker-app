package com.example.aquatracker.util

import java.math.RoundingMode
import java.text.DecimalFormat

object NumberFormatter {
    private val deltaFormat = DecimalFormat("+#.##;-#.##").apply {
        roundingMode = RoundingMode.HALF_UP
    }

    fun formatDelta(value: Double): String = deltaFormat.format(value)
}