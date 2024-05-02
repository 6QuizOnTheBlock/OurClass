package com.sixkids.teacher.challenge.create.info

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(

) : BaseViewModel<InfoState, InfoEffect>(
    InfoState()
) {
    fun updateTitle(it: String) {
        intent {
            copy(title = it)
        }
    }
}
