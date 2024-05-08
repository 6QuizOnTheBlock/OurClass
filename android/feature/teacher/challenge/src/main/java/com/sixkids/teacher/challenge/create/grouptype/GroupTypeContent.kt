package com.sixkids.teacher.challenge.create.grouptype

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.textfield.InputTextType
import com.sixkids.designsystem.component.textfield.UlbanUnderLineTextField
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.challenge.R
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle


@Composable
fun GroupTypeRoute(
    viewModel: GroupTypeViewModel = hiltViewModel(),
    updateMinCount: (String) -> Unit,
    updateGroupType: (GroupType) -> Unit,
    moveToResult: () -> Unit,
    moveNextStep: () -> Unit,
    createChallenge: () -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    val context = LocalContext.current
    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is GroupTypeEffect.ShowInputErrorSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.please_input_min_head_count),
                )
            )

            is GroupTypeEffect.MoveToResult -> moveToResult()
            is GroupTypeEffect.MoveToMatchingStep -> moveNextStep()
            is GroupTypeEffect.UpdateGroupType -> updateGroupType(it.type)
            is GroupTypeEffect.UpdateMinCount -> updateMinCount(it.minCount)
            GroupTypeEffect.CreateChallenge -> createChallenge()
        }
    }

    GroupTypeScreen(
        uiState = uiState,
        updateMinCount = viewModel::updateMinCount,
        updateGroupType = viewModel::updateGroupType,
        moveNextStep = viewModel::moveNextStep,
        moveToMatchingStep = viewModel::moveToMatchingStep,
        createChallenge = viewModel::createChallenge
    )
}

@Composable
fun GroupTypeScreen(
    uiState: GroupTypeState,
    updateMinCount: (String) -> Unit = {},
    updateGroupType: (GroupType) -> Unit = {},
    createChallenge: () -> Unit = {},
    moveNextStep: () -> Unit = {},
    moveToMatchingStep: () -> Unit = {},
) {

    val minCountFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val radioOptions = GroupType.entries
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    fun handelNext() {
        if (uiState.groutTypeVisibility.not()) {
            moveNextStep()
        } else if (uiState.groutTypeVisibility && uiState.groutType == GroupType.FREE) {
            createChallenge()
        } else {
            moveToMatchingStep()
        }
    }
    LaunchedEffect(key1 = selectedOption) {
        updateGroupType(selectedOption)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { focusManager.clearFocus() }
                )
            },
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimatedVisibility(uiState.groutTypeVisibility) {
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(
                    text = stringResource(R.string.please_select_group_type),
                    style = UlbanTypography.titleSmall
                )
                radioOptions.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (option == selectedOption),
                                onClick = { onOptionSelected(option) },
                                role = Role.RadioButton
                            )
                            .padding(start = 8.dp, top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = null
                        )
                        Text(
                            text = stringResource(option.textRes),
                            style = UlbanTypography.bodyMedium,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
        Column {
            Text(
                text = stringResource(R.string.please_input_min_head_count),
                style = UlbanTypography.titleSmall
            )
            UlbanUnderLineTextField(
                modifier = Modifier
                    .focusRequester(minCountFocusRequester),
                text = uiState.minCount,
                onTextChange = { updateMinCount(it) },
                onIconClick = { updateMinCount("") },
                inputTextType = InputTextType.PEOPLE,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        handelNext()
                    }
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        UlbanFilledButton(
            text = if (uiState.groutType == GroupType.FREE)
                stringResource(R.string.confirm)
            else
                stringResource(R.string.next),
            onClick = {
                handelNext()
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }


}


@Preview(showBackground = true)
@Composable
fun GroupTypeScreenPreview() {
    UlbanTheme {
        GroupTypeScreen(
            GroupTypeState(groutTypeVisibility = true)
        )
    }
}
