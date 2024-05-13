package com.sixkids.student.main.joinorganization

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.sixkids.designsystem.component.textfield.UlbanUnderLineTextField
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.student.main.R
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun JoinOrganizationRoute(
    viewModel: JoinOrganizationViewModel = hiltViewModel(),
    navigateToStudentOrganizationList: () -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            JoinOrganizationEffect.NavigateToOrganizationList -> navigateToStudentOrganizationList()
            is JoinOrganizationEffect.OnShowSnackBar -> onShowSnackBar(sideEffect.tkn)
        }
    }

    JoinOrganizationScreen(
        uiState = uiState,
        onJoinOrganizationClick = viewModel::joinOrganizationClick,
        onBackClick = onBackClick,
        onUpdateCode = viewModel::updateCode,
        onUpdateId = viewModel::updateId
    )
}

@Composable
fun JoinOrganizationScreen(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    uiState: JoinOrganizationState = JoinOrganizationState(),
    onJoinOrganizationClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onUpdateCode: (String) -> Unit = {},
    onUpdateId: (String) -> Unit = {},
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            UlbanTopSection(stringResource(R.string.join_organization_top), onBackClick)

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = stringResource(id = R.string.join_organization_title),
                style = UlbanTypography.bodyLarge,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )

            UlbanUnderLineTextField(
                text = uiState.code,
                hint = stringResource(R.string.join_organization_code_hint),
                onTextChange = onUpdateCode,
                onIconClick = {
                    onUpdateCode("")
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.join_organization_id),
                style = UlbanTypography.bodyLarge,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )

            UlbanUnderLineTextField(
                text = uiState.id,
                hint = stringResource(R.string.join_organization_id_hint),
                onTextChange = onUpdateId,
                onIconClick = {
                    onUpdateId("")
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            UlbanFilledButton(
                text = stringResource(R.string.profile_done),
                onClick = { onJoinOrganizationClick() },
                modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
@Preview(showBackground = true)
fun JoinOrganizationScreenPreview() {
    UlbanTheme {
        JoinOrganizationScreen()
    }
}