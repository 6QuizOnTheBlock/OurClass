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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.RedDark
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.manageclass.R
import com.sixkids.teacher.manageclass.setting.component.SimpleNumberOutlinedTextField
import com.sixkids.teacher.manageclass.setting.component.SimpleOutlinedTextField
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun ClassSettingRoute(
    padding: PaddingValues,
    viewModel: ClassSettingViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectWithLifecycle {
        when (it){
            ClassSettingEffect.navigateBack -> navigateBack()
            is ClassSettingEffect.onShowSnackBar -> onShowSnackBar(SnackbarToken(it.message))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadSelectedOrganizationName()
    }

    ClassSettingScreen(
        classSettingState = uiState,
        cancelButtonOnClick = navigateBack,
        confirmButtonOnClick = viewModel::updateClassName,
        onSchoolTextChange = viewModel::onSchoolNameChanged,
        onGradeTextChange = viewModel::onGradeChanged,
        onClassNumberTextChange = viewModel::onClassNumberChanged
    )
}

@Composable
fun ClassSettingScreen(
    modifier: Modifier = Modifier,
    classSettingState: ClassSettingState = ClassSettingState(),
    cancelButtonOnClick: () -> Unit = {},
    confirmButtonOnClick: () -> Unit = {},
    onSchoolTextChange: (String) -> Unit = {},
    onGradeTextChange: (String) -> Unit = {},
    onClassNumberTextChange: (String) -> Unit = {}
) {
    val gradeString = classSettingState.grade?.toString() ?: ""
    val classNumberString = classSettingState.classNumber?.toString() ?: ""

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
                text = classSettingState.schoolName,
                onTextChange = onSchoolTextChange
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.setting_class_grade),
                style = UlbanTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            SimpleNumberOutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = gradeString,
                postfix = stringResource(id = R.string.setting_class_grade),
                onTextChange = {onGradeTextChange(it)}
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.setting_class_class_number),
                style = UlbanTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            SimpleNumberOutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = classNumberString,
                postfix = stringResource(id = R.string.setting_class_class_number),
                onTextChange = {onClassNumberTextChange(it)}
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
                    onClick = confirmButtonOnClick,
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