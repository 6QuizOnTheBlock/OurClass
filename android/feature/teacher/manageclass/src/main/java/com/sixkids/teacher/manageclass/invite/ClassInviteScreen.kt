package com.sixkids.teacher.manageclass.invite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.theme.Green
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.manageclass.R
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun ClassInviteRoute(
    padding: PaddingValues
) {
    Box(modifier = Modifier.padding(padding)) {
        ClassInviteScreen()
    }
}

@Composable
fun ClassInviteScreen(
    modifier: Modifier = Modifier,
    classInviteState: ClassInviteState = ClassInviteState(),
    createInviteCodeButtonOnClick: () -> Unit = {},
    copyInviteCodeOnClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        UlbanDetailAppBar(
            leftIcon = UlbanRes.drawable.invite,
            title = stringResource(id = R.string.manage_class_invite),
            content = stringResource(id = R.string.manage_class_invite),
            topDescription = "",
            bottomDescription = classInviteState.classString,
            color = Green
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (classInviteState.classInviteCode == null) {
                UlbanFilledButton(
                    text = stringResource(id = R.string.invite_create_new),
                    color = Green,
                    onClick = createInviteCodeButtonOnClick
                )
            } else {
                Text(
                    text = stringResource(id = R.string.invite_code_created),
                    style = UlbanTypography.bodyLarge
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = classInviteState.classInviteCode,
                        style = UlbanTypography.titleLarge.copy(
                            fontSize = 34.sp
                        )
                    )
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { copyInviteCodeOnClick() },
                        imageVector = ImageVector.vectorResource(id = UlbanRes.drawable.ic_copy),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.invite_code_end),
                    style = UlbanTypography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClassInviteScreenPreview() {
    ClassInviteScreen(
        classInviteState = ClassInviteState(
            classString = "구미초등학교 1학년 1반",
            classInviteCode = "123456"
        )
    )
}