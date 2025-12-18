package com.example.aquatracker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditAquaDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, onCancel: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(color = MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Text(
                text = "Добавление/Редактирование показания",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            var aquaValue by remember { mutableStateOf("") }
            var showModal by remember { mutableStateOf(false) }
            var selectedDate by remember { mutableStateOf<Long?>(null) }

            if (showModal) {
                CustomDatePicker(
                    onDateSelected = { selectedDate = it },
                    onDismiss = { showModal = false }
                )
            }

            TextField(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                value = aquaValue,
                onValueChange = { aquaValue = it },
                label = { Text("Показание") },
                singleLine = true,
                maxLines = 1
            )

            TextField(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .pointerInput(selectedDate) {
                        awaitEachGesture {
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null) {
                                showModal = true
                            }
                        }
                    },
                value = selectedDate?.let { convertMillisToDate(it) } ?: "",
                onValueChange = {},
                label = { Text("Дата снятия показания") },
                readOnly = true,
                singleLine = true,
                maxLines = 1
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = { onConfirm() }) {
                    Text("Сохранить")
                }

                FilledTonalButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { onCancel() }) {
                    Text("Удалить")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
