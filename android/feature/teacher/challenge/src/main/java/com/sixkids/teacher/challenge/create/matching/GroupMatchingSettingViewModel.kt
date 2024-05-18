package com.sixkids.teacher.challenge.create.matching

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupMatchingSettingViewModel @Inject constructor(

) : BaseViewModel<GroupMatchingSettingState, GroupMatchingSettingEffect>(
    GroupMatchingSettingState()
) {

}
