package com.sixkids.teacher.manageclass.setting

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.LoadSelectedOrganizationNameUseCase
import com.sixkids.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClassSettingViewModel @Inject constructor(
    private val loadSelectedOrganizationNameUseCase: LoadSelectedOrganizationNameUseCase
) : BaseViewModel<ClassSettingState, ClassSettingEffect>(ClassSettingState()) {

    fun loadSelectedOrganizationName() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }

            loadSelectedOrganizationNameUseCase()
                .onSuccess {

                }
                .onFailure {

                }

            intent { copy(isLoading = false) }
        }
    }

    private fun separateClassString(classString: String){
        var school_name = ""
        var grade = 0
        var classNumber = 0
        val school_gradeClass = classString.split("\n").also {
            school_name = it[0]
        }
        val grade_class = school_gradeClass[1].split("학년").also {
            grade = it[0].toInt()
        }


    }
}