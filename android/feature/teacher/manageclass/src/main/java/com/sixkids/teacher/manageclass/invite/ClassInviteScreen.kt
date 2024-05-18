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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.theme.Green
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.manageclass.R
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun ClassInviteRoute(
    viewModel: ClassInviteViewModel = hiltViewModel(),
    padding: PaddingValues,
    onShowSnackBar: (SnackbarToken) -> Unit
) {
    var copyTrigger by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is ClassInviteEffect.onShowSnackBar -> onShowSnackBar(SnackbarToken(it.message))
        }
    }

    if (copyTrigger){
        if (uiState.classId == null || uiState.classInviteCode == null){
            onShowSnackBar(SnackbarToken("초대 코드를 복사 할 수 없습니다."))
        } else {
            copyToClipboardClassInviteCode(uiState.classId!!, uiState.classInviteCode!!)
        }
        copyTrigger = false
    }

    Box(modifier = Modifier.padding(padding)) {
        ClassInviteScreen(
            classInviteState = uiState,
            createInviteCodeButtonOnClick = { viewModel.getInviteCode() },
            copyInviteCodeOnClick = { copyTrigger = true }
        )
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

@Composable
private fun copyToClipboardClassInviteCode(classId: Int, classInviteCode: String) {
    LocalClipboardManager.current.let { manager ->
        manager.setText(AnnotatedString(
            "학급 아이디 : ${classId}\n초대 코드 : ${classInviteCode}"
        ))
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