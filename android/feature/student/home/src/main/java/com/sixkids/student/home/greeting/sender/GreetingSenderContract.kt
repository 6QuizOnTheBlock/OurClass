package com.sixkids.student.home.greeting.sender

import com.sixkids.model.GreetingNFC
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class GreetingSenderState(
    val greetingNfc: GreetingNFC = GreetingNFC()
): UiState

sealed interface GreetingSenderEffect: SideEffect {

}