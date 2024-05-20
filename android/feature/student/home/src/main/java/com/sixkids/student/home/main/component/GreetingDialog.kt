package com.sixkids.student.home.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sixkids.designsystem.component.dialog.UlbanBasicDialog
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.BlueDark
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.RedDark
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.R as DesignR

@Composable
fun GreetingDialog(
    senderClick: () -> Unit = {},
    receiverClick: () -> Unit = {},
    cancelClick: () -> Unit = {}
) {
    UlbanBasicDialog {

        Column(
            modifier = Modifier
                .width(280.dp)
                .padding(vertical = 16.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = DesignR.drawable.ic_cancel),
                contentDescription = "cancel",
                modifier = Modifier.align(Alignment.End).size(32.dp).clickable {
                    cancelClick()
                }
            )

            AsyncImage(
                model = DesignR.drawable.tag_hello,
                modifier = Modifier.fillMaxWidth(),
                placeholder = painterResource(id = DesignR.drawable.relay_tag),
                contentDescription = "tag"
            )

            Text(
                text = "친구와 인사를 하고\n점수를 올려봐요",
                style = UlbanTypography.titleSmall,
                textAlign = TextAlign.Center
            )

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Red),
                    colors = ButtonDefaults.buttonColors(containerColor = Red),
                    onClick = { senderClick() }) {
                    Text(
                        text = "친구에게\n인사 건네기",
                        style = UlbanTypography.titleSmall.copy(
                            fontSize = 14.sp,
                            lineHeight = 24.sp,
                            color = RedDark
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Blue),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    onClick = { receiverClick() }) {
                    Text(
                        text = "친구의\n인사 받기",
                        style = UlbanTypography.titleSmall.copy(
                            fontSize = 14.sp,
                            lineHeight = 24.sp,
                            color = BlueDark
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }


}

@Composable
@Preview(showBackground = true)
fun GreetingDialogPreview() {
    GreetingDialog()
}