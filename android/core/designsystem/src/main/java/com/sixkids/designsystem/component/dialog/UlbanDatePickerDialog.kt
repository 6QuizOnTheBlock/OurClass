package com.sixkids.designsystem.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.UlbanTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UlbanDatePickerDialog(
    selectedDate: LocalDate = LocalDate.now(),
    onDismiss: () -> Unit,
    onClickConfirm: (date: LocalDate) -> Unit
) {

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = selectedDate.plusDays(1)
            .atStartOfDay(ZoneId.systemDefault()).toInstant()
            .toEpochMilli()
    )


    DatePickerDialog(
        onDismissRequest = { onDismiss() }, confirmButton = {
            TextButton(
                onClick = {
                    onClickConfirm(datePickerState.selectedDateMillis?.let {
                        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                    } ?: LocalDate.now())
                },
            ) {
                Text(stringResource(R.string.confirm))
            }
        }, dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(stringResource(R.string.cancel))
            }
        }, colors = DatePickerDefaults.colors(
            containerColor = Cream
        )
    ) {
        DatePicker(
            state = datePickerState,
        )
    }
}


@Preview
@Composable
fun CustomDatePickerDialogPreview() {
    UlbanTheme {
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        var showDialog by remember { mutableStateOf(false) }
        Column {
            Text(
                text = selectedDate.toString(),
            )
            if (showDialog) {
                UlbanDatePickerDialog(
                    selectedDate = selectedDate,
                    onDismiss = {
                        showDialog = false
                    },
                    onClickConfirm = {
                        selectedDate = it
                        showDialog = false
                    }
                )
            }
            Button(
                onClick = {
                    showDialog = true
                }
            ) {
                Text("날짜 선택")
            }
        }
    }

}
