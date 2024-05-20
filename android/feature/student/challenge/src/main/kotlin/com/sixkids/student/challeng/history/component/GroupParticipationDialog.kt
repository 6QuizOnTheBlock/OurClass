package com.sixkids.student.challeng.history.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.dialog.UlbanBasicDialog
import com.sixkids.designsystem.theme.Green
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.student.challenge.R

@Composable
fun GroupParticipationDialog(
    modifier: Modifier = Modifier,
    onCreateGroupClick: () -> Unit,
    onJoinGroupClick: () -> Unit,
    onDismiss: () -> Unit
) {
    UlbanBasicDialog(
        onDismiss = onDismiss,
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.width(240.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                TextButton(
                    modifier = Modifier
                        .width(100.dp)
                        .height(140.dp),
                    onClick = onCreateGroupClick,
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Orange
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.create_group),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        style = UlbanTypography.titleSmall
                    )
                }
                TextButton(
                    modifier = Modifier
                        .width(100.dp)
                        .height(140.dp),
                    onClick = onJoinGroupClick,
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Green
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.join_group),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        style = UlbanTypography.titleSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            UlbanFilledButton(
                text = stringResource(R.string.cancel),
                onClick = onDismiss
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GroupParticipationDialogPreview() {
    GroupParticipationDialog(
        onCreateGroupClick = {},
        onJoinGroupClick = {},
        onDismiss = {}
    )
}
