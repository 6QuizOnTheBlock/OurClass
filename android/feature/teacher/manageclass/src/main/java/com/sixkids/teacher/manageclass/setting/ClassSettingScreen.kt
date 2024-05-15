package com.sixkids.teacher.manageclass.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.RedDark
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.manageclass.R
import com.sixkids.teacher.manageclass.setting.component.SimpleOutlinedTextField
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun ClassSettingRoute(
    padding: PaddingValues
) {
    ClassSettingScreen()
}

@Composable
fun ClassSettingScreen(
    modifier: Modifier = Modifier,
    classSettingState: ClassSettingState = ClassSettingState(),
    cancelButtonOnClick: () -> Unit = {},
    confirmButtonOnClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        UlbanDetailAppBar(
            leftIcon = UlbanRes.drawable.setting,
            title = stringResource(id = R.string.manage_class_setting),
            content = stringResource(id = R.string.manage_class_setting),
            topDescription = "",
            bottomDescription = classSettingState.classString,
            color = Red
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.setting_class_school_name),
                style = UlbanTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            SimpleOutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.setting_class_grade),
                style = UlbanTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            SimpleOutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.setting_class_class_number),
                style = UlbanTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            SimpleOutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row {
                UlbanFilledButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.cancel),
                    onClick = cancelButtonOnClick,
                    color = Red,
                    textColor = RedDark
                )
                Spacer(modifier = Modifier.width(10.dp))
                UlbanFilledButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.ok),
                    onClick = cancelButtonOnClick,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClassSettingScreenPreview() {
    ClassSettingScreen()
}