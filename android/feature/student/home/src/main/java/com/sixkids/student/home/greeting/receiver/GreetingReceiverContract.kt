package com.sixkids.student.home.greeting.receiver

import com.sixkids.model.GreetingNFC
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class GreetingReceiverState(
    val organizationId: Int = -1,
    val greetingNfc: GreetingNFC = GreetingNFC()
): UiState

sealed interface GreetingReceiverEffect: SideEffect{
    data class OnShowSnackBar(val tkn : SnackbarToken) : GreetingReceiverEffect

    data object NavigateToHome : GreetingReceiverEffect
}