package com.sixkids.designsystem.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sixkids.designsystem.theme.UlbanTheme
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UlbanTimePickerDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onClickConfirm: (date: LocalTime) -> Unit,
    selectedTime: LocalTime = LocalTime.now(),
) {
    UlbanBasicDialog(
        modifier = modifier,
        onDismiss = onDismiss,
    ) {
        val timePickerState = rememberTimePickerState()

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
        ) {
            TimePicker(state = timePickerState)
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    Text("취소")
                }
                TextButton(
                    onClick = {
                        onClickConfirm(selectedTime)
                    }
                ) {
                    Text("확인")
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun UlbanTimePickerDialogPreview() {

    UlbanTheme {
        var selectedTime by remember { mutableStateOf(LocalTime.now()) }
        var showDialog by remember { mutableStateOf(false) }

        UlbanTimePickerDialog(
            selectedTime = selectedTime,
            onDismiss = {
                showDialog = false
            },
            onClickConfirm = {
                selectedTime = it
                showDialog = false
            }
        )
    }
}
