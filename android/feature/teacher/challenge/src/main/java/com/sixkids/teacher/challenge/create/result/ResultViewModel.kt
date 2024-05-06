package com.sixkids.teacher.challenge.create.result

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ResultViewModel @Inject constructor(

) : BaseViewModel<ResultState, ResultEffect>(ResultState()) {
    fun showResultDialog() {
        postSideEffect(ResultEffect.ShowResultDialog)
    }

}
