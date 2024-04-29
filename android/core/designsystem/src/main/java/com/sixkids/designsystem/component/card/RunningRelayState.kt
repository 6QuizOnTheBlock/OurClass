package com.sixkids.designsystem.theme.component.card

interface RunningState{
    val totalStudent: Int
    val successStudent: Int
}

data class RunningTogetherState(
    val date: Long,
    val endDate: Long,
    override val totalStudent: Int,
    override val successStudent: Int,
) : RunningState

data class RunningRelayState(
    val question: String,
    val nowStudent: String,
    override val totalStudent: Int,
    override val successStudent: Int,
) : RunningState
