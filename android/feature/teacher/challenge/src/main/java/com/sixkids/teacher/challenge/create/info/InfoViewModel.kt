package com.sixkids.teacher.challenge.create.info

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(

) : BaseViewModel<InfoState, InfoEffect>(
    InfoState()
) {
    fun updateTitle(title: String) {
        intent {
            postSideEffect(InfoEffect.UpdateTitle(title))
            copy(title = title)
        }
    }

    fun updateContent(content: String) {
        intent {
            postSideEffect(InfoEffect.UpdateContent(content))
            copy(content = content)
        }
    }

    fun updatePoint(point: String) {
        intent {
            postSideEffect(InfoEffect.UpdatePoint(point))
            copy(point = point)
        }
    }

    fun updateStartTime(startTime: LocalDateTime) {
        intent {
            postSideEffect(InfoEffect.UpdateStartTime(startTime))
            copy(startTime = startTime)
        }
    }

    fun updateEndTime(endTime: LocalDateTime) {
        intent {
            postSideEffect(InfoEffect.UpdateEndTime(endTime))
            copy(endTime = endTime)
        }
    }
}
