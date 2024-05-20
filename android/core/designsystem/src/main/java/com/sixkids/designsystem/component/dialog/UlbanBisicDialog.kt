package com.sixkids.designsystem.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.UlbanTheme

@Composable
fun UlbanBasicDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    backGroundColor: Color = Cream,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = backGroundColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UlbalDialogPreview() {
    UlbanTheme {
        UlbanBasicDialog {
            Text(text = "Hello World")
        }
    }
}
