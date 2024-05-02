package com.sixkids.teacher.challenge.create

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChallengeCreateViewModel @Inject constructor(

) : BaseViewModel<ChallengeCreateUiState, ChallengeCreateEffect>(
    ChallengeCreateUiState()
) {


    private var title: String = ""
    private var content: String = ""
    private var startTime: LocalDateTime = LocalDateTime.now()
    private var endTime: LocalDateTime = LocalDateTime.now()
    private var point: Int = 0



}
