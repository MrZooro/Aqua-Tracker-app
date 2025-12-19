package com.example.aquatracker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.aquatracker.R
import com.example.aquatracker.util.DateFormatter
import com.example.aquatracker.util.extension.formatDate
import com.example.aquatracker.viewModel.dataClass.AquaItem
import java.time.Instant
import java.time.ZoneOffset
import java.util.Calendar

@Composable
fun EditAquaDialog(
    aquaItem: AquaItem? = null,
    onDismiss: () -> Unit,
    onConfirm: (value: Double, date: Long) -> Unit,
    onCancel: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(color = MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            val dialogTitle = if (aquaItem == null) {
                stringResource(R.string.add_value_title)
            } else {
                stringResource(R.string.edit_value_title)
            }

            Text(
                text = dialogTitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            var aquaValue by remember { mutableStateOf(aquaItem?.value?.toString() ?: "") }
            var showModal by remember { mutableStateOf(false) }
            var selectedDate by remember { mutableStateOf(aquaItem?.date) }

            val valueDouble = aquaValue.toDoubleOrNull()
            val isFormValid = valueDouble != null && selectedDate != null

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
                label = { Text(stringResource(R.string.value_text_field_label)) },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )
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
                    }
                    .focusable(false),
                value = selectedDate?.formatDate(DateFormatter.fullDate) ?: "",
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                maxLines = 1,
                placeholder = { Text(stringResource(R.string.date_text_field_hint)) }
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = {
                        valueDouble?.let { itemValue ->
                            selectedDate?.let { itemDate ->
                                onConfirm(itemValue, itemDate)
                                onDismiss()
                            }
                        }
                    },
                    enabled = isFormValid
                ) {
                    Text(stringResource(R.string.save_btn_title))
                }

                FilledTonalButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { onCancel() }) {
                    Text(stringResource(R.string.cancel_dialog_btn))
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
    val calendar = Calendar.getInstance()
    val todayInMillis = calendar.timeInMillis

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = todayInMillis,
        yearRange = IntRange(2000, calendar.get(Calendar.YEAR)),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= todayInMillis
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year <= calendar.get(Calendar.YEAR)
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val epochDay = datePickerState.selectedDateMillis
                    ?.let { millis ->
                        Instant.ofEpochMilli(millis)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate()
                            .toEpochDay()
                    }

                onDateSelected(epochDay)
                onDismiss()
            }) {
                Text(stringResource(R.string.confirm_dialog_btn))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel_dialog_btn))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
