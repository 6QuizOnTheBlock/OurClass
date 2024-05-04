package com.sixkids.teacher.challenge.create.info

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.dialog.UlbanDatePicker
import com.sixkids.designsystem.component.textfield.InputTextType
import com.sixkids.designsystem.component.textfield.UlbanUnderLineIconInputField
import com.sixkids.designsystem.component.textfield.UlbanUnderLineTextField
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.challenge.R
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import com.sixkids.ui.util.formatToDayMonthYear
import com.sixkids.ui.util.formatToHourMinute
import java.time.LocalDate
import java.time.LocalDateTime
import com.sixkids.designsystem.R as DesignSystemR

private const val TAG = "D107"

@Composable
fun InfoContentRoute(
    viewModel: InfoViewModel = hiltViewModel(),
    updateTitle: (String) -> Unit,
    updateContent: (String) -> Unit,
    updateStartTime: (LocalDateTime) -> Unit,
    updateEndTime: (LocalDateTime) -> Unit,
    updatePoint: (String) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    moveNextStep: () -> Unit,
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.setInitVisibility()
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    Log.d(TAG, "InfoContentRoute: ${uiState.step}")

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is InfoEffect.UpdateTitle -> updateTitle(it.title)
            is InfoEffect.UpdateContent -> updateContent(it.content)
            is InfoEffect.UpdateStartTime -> updateStartTime(it.startTime)
            is InfoEffect.UpdateEndTime -> updateEndTime(it.endTime)
            is InfoEffect.UpdatePoint -> updatePoint(it.point)
            is InfoEffect.ShowInputErrorSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.please_input_all_info)
                )
            )

            InfoEffect.MoveGroupTypeStep -> moveNextStep()
        }
    }

    InfoContent(
        uiState = uiState,
        updateTitle = viewModel::updateTitle,
        updateContent = viewModel::updateContent,
        updatePoint = viewModel::updatePoint,
        updateStartDate = viewModel::updateStartDate,
        updateEndDate = viewModel::updateEndDate,
        updateStartTime = viewModel::updateStartTime,
        updateEndTime = viewModel::updateEndTime,
        moveNextInput = viewModel::moveNextInput,
        moveNextStep = viewModel::moveNextStep,
        focusChange = viewModel::focusChange
    )

}

@Composable
fun InfoContent(
    uiState: InfoState = InfoState(),
    updateTitle: (String) -> Unit = {},
    updateContent: (String) -> Unit = {},
    updateStartDate: (LocalDate) -> Unit = {},
    updateEndDate: (LocalDate) -> Unit = {},
    updateStartTime: (LocalDateTime) -> Unit = {},
    updateEndTime: (LocalDateTime) -> Unit = {},
    updatePoint: (String) -> Unit = {},
    moveNextInput: () -> Unit = {},
    moveNextStep: () -> Unit = {},
    focusChange: (InfoStep) -> Unit = {}
) {

    val titleFocusRequester = remember { FocusRequester() }
    val contentFocusRequester = remember { FocusRequester() }
    val pointFocusRequester = remember { FocusRequester() }


    val focusManager = LocalFocusManager.current

    val handelNext: () -> Unit = {
        if (uiState.step != InfoStep.POINT) {
            moveNextInput()
        } else {
            moveNextStep()
        }
    }


    LaunchedEffect(key1 = uiState.step) {
        if (uiState.stepVisibilityList.isNotEmpty() && uiState.stepVisibilityList[uiState.step.ordinal]) {
            when (uiState.step) {
                InfoStep.TITLE -> titleFocusRequester.requestFocus()
                InfoStep.POINT -> pointFocusRequester.requestFocus()
                else -> {}
            }
        }
    }

    LaunchedEffect(key1 = uiState.endDate) {
        Log.d(TAG, "InfoContent: ${uiState.endDate}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { focusManager.clearFocus() }
                )
            }
    ) {
        if (uiState.stepVisibilityList.isNotEmpty()) {
            AnimatedVisibility(uiState.stepVisibilityList[InfoStep.POINT.ordinal]) {
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(
                        text = stringResource(R.string.please_input_point),
                        style = UlbanTypography.titleSmall
                    )
                    UlbanUnderLineTextField(
                        modifier = Modifier
                            .focusRequester(pointFocusRequester)
                            .onFocusChanged {
                                focusChange(InfoStep.POINT)
                            },
                        text = uiState.point,
                        onTextChange = { updatePoint(it) },
                        hint = stringResource(R.string.point_hint),
                        onIconClick = { updatePoint("") },
                        inputTextType = InputTextType.POINT,
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
                LaunchedEffect(key1 = uiState.stepVisibilityList[InfoStep.POINT.ordinal]) {
                    pointFocusRequester.requestFocus()
                }
            }
            AnimatedVisibility(uiState.stepVisibilityList[InfoStep.END_TIME.ordinal]) {
                var showDateDialog by remember { mutableStateOf(false) }
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(
                        text = stringResource(R.string.end_time),
                        style = UlbanTypography.titleSmall
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        UlbanUnderLineIconInputField(
                            modifier = Modifier.weight(3f),
                            text = uiState.endDate.formatToDayMonthYear(),
                            iconResource = DesignSystemR.drawable.ic_calendar,
                            onIconClick = {
                                showDateDialog = true
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        UlbanUnderLineIconInputField(
                            modifier = Modifier.weight(2f),
                            text = uiState.endTime.formatToHourMinute(),
                            iconResource = DesignSystemR.drawable.ic_time,
                            onIconClick = {
                                //시계 띄우기
                            }
                        )
                    }
                    if(showDateDialog){
                        UlbanDatePicker(
                            selectedDate = uiState.endDate,
                            onDismiss = {
                                showDateDialog = false
                            },
                            onClickConfirm = {
                                showDateDialog = false
                                updateEndDate(it)
                            }
                        )
                    }
                }
            }
            AnimatedVisibility(uiState.stepVisibilityList[InfoStep.START_TIME.ordinal]) {
                var showDateDialog by remember { mutableStateOf(false) }
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(
                        text = stringResource(R.string.start_time),
                        style = UlbanTypography.titleSmall
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        UlbanUnderLineIconInputField(
                            modifier = Modifier.weight(3f),
                            text = uiState.startDate.formatToDayMonthYear(),
                            iconResource = DesignSystemR.drawable.ic_calendar,
                            onIconClick = {
                                showDateDialog = true
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        UlbanUnderLineIconInputField(
                            modifier = Modifier.weight(2f),
                            text = uiState.startTime.formatToHourMinute(),
                            iconResource = DesignSystemR.drawable.ic_time,
                            onIconClick = {
                                //시계 띄우기
                            }
                        )
                    }
                }
                if(showDateDialog){
                    UlbanDatePicker(
                        selectedDate = uiState.startDate,
                        onDismiss = {
                            showDateDialog = false
                        },
                        onClickConfirm = {
                            showDateDialog = false
                            updateStartDate(it)
                        }
                    )
                }
            }
            AnimatedVisibility(uiState.stepVisibilityList[InfoStep.CONTENT.ordinal]) {
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(
                        text = stringResource(R.string.please_input_content),
                        style = UlbanTypography.titleSmall
                    )
                    UlbanUnderLineTextField(
                        modifier = Modifier
                            .focusRequester(contentFocusRequester)
                            .onFocusChanged {
                                focusChange(InfoStep.CONTENT)
                            },
                        text = uiState.content,
                        onTextChange = { updateContent(it) },
                        onIconClick = { updateContent("") },
                        inputTextType = InputTextType.TEXT,
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
                LaunchedEffect(key1 = uiState.stepVisibilityList[InfoStep.CONTENT.ordinal]) {
                    contentFocusRequester.requestFocus()
                }
            }
            AnimatedVisibility(uiState.stepVisibilityList[InfoStep.TITLE.ordinal]) {
                Column {
                    Text(
                        text = stringResource(R.string.please_input_title),
                        style = UlbanTypography.titleSmall
                    )
                    UlbanUnderLineTextField(
                        modifier = Modifier
                            .focusRequester(titleFocusRequester)
                            .onFocusChanged {
                                focusChange(InfoStep.TITLE)
                            },
                        text = uiState.title,
                        onTextChange = { updateTitle(it) },
                        onIconClick = { updateTitle("") },
                        inputTextType = InputTextType.TEXT,
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
                LaunchedEffect(key1 = uiState.stepVisibilityList[InfoStep.TITLE.ordinal]) {
                    titleFocusRequester.requestFocus()
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        UlbanFilledButton(
            text = stringResource(R.string.next),
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
fun InfoContentPreview() {
    UlbanTheme {
        InfoContent(
            uiState = InfoState(
                stepVisibilityList = listOf(true, true, true, true, true),
            )
        )
    }
}
