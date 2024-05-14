package com.sixkids.student.group.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.dialog.UlbanBasicDialog
import com.sixkids.designsystem.theme.Red
import com.sixkids.student.challenge.R
import com.sixkids.designsystem.R as DesignSystemR

@Composable
fun InviteDialog(
    leader: MemberIconItem,
    modifier: Modifier = Modifier,
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {

    UlbanBasicDialog(
        modifier = modifier,
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MemberIcon(modifier = Modifier.padding(top = 4.dp), member = leader)
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = stringResource(R.string.invited_group)
            )

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                UlbanFilledButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = DesignSystemR.string.cancel),
                    onClick = onCancelClick,
                    color = Red
                )
                UlbanFilledButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = DesignSystemR.string.confirm),
                    onClick = onConfirmClick
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun InviteDialogPreview() {
    InviteDialog(
        leader = MemberIconItem(
            memberId = 1,
            name = "Leader",
            photo = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
            isActive = true
        )
    )
}
