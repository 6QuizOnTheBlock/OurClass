package com.sixkids.teacher.manageclass.chattingfilter.component

import android.service.autofill.OnClickAction
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.dialog.UlbanBasicDialog
import com.sixkids.designsystem.component.textfield.UlbanBasicTextField
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.manageclass.R

@Composable
fun ChatFilterWordDialog(
    cancelButtonOnClick: () -> Unit = {},
    confirmButtonOnClick: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }

    UlbanBasicDialog {
        Column(
            modifier = Modifier.width(240.dp)
        ) {
            Text(
                text = stringResource(id = R.string.chatting_filter_word),
                style = UlbanTypography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                UlbanBasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = text,
                    onTextChange = { text = it },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                // 취소 버튼
                UlbanFilledButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.cancel),
                    color = Red,
                    onClick = cancelButtonOnClick
                )
                Spacer(modifier = Modifier.width(4.dp))
                UlbanFilledButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.ok),
                    onClick = {confirmButtonOnClick(text)}
                )
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun ChatFilterWordDialogPreview() {
    UlbanBasicDialog {
        ChatFilterWordDialog()
    }
}