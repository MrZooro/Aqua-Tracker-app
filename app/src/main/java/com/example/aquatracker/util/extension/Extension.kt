package com.example.aquatracker.util.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Long.formatDate(formatter: DateTimeFormatter): String =
    LocalDate.ofEpochDay(this).format(formatter)