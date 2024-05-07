package com.sixkids.teacher.main.neworganization

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.screen.UlbanTopSection
import com.sixkids.designsystem.component.textfield.InputTextType
import com.sixkids.designsystem.component.textfield.UlbanUnderLineTextField
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.main.R
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun NewOrganizationRoute(
    viewModel: NewOrganizationViewModel = hiltViewModel(),
    navigateToOrganizationList: () -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            NewOrganizationEffect.NavigateToOrganizationList -> navigateToOrganizationList()
            is NewOrganizationEffect.OnShowSnackBar -> onShowSnackBar(sideEffect.tkn)
        }
    }

    NewOrganizationScreen(
        uiState = uiState,
        onNewClassClick = viewModel::newClassClick,
        onBackClick = onBackClick,
        onUpdateName = viewModel::updateSchoolName,
        onUpdateGrade = viewModel::updateSchoolGrade,
        onUpdateClass = viewModel::updateSchoolClass
    )
}

@Composable
fun NewOrganizationScreen(
    uiState: NewOrganizationState = NewOrganizationState(),
    onNewClassClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onUpdateName: (String) -> Unit = {},
    onUpdateGrade: (String) -> Unit = {},
    onUpdateClass: (String) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            UlbanTopSection(stringResource(id = R.string.new_organization_title), onBackClick)

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = stringResource(id = R.string.new_organization_name),
                style = UlbanTypography.bodyLarge,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
            UlbanUnderLineTextField(
                text = uiState.name,
                hint = "학교명을 입력해주세요",
                onTextChange = onUpdateName,
                onIconClick = {
                    onUpdateName("")
                }
            )

            Text(
                text = stringResource(id = R.string.new_organization_grade),
                style = UlbanTypography.bodyLarge,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            )
            UlbanUnderLineTextField(
                text = uiState.grade,
                hint = "학년을 입력해주세요",
                inputTextType = InputTextType.GRADE,
                onTextChange = onUpdateGrade,
                onIconClick = {
                    onUpdateGrade("")
                }
            )

            Text(
                text = stringResource(id = R.string.new_organization_class),
                style = UlbanTypography.bodyLarge,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            )
            UlbanUnderLineTextField(
                text = uiState.classNo,
                hint = "반을 입력해주세요",
                inputTextType = InputTextType.CLASS,
                onTextChange = onUpdateClass,
                onIconClick = {
                    onUpdateClass("")
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            UlbanFilledButton(
                text = "완료",
                onClick = onNewClassClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NewOrganizationScreenPreview() {
    UlbanTheme {
        NewOrganizationScreen()
    }
}