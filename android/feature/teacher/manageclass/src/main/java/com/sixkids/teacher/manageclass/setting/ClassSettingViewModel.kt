package com.sixkids.teacher.manageclass.setting

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.organization.LoadSelectedOrganizationNameUseCase
import com.sixkids.domain.usecase.organization.SaveSelectedOrganizationNameUseCase
import com.sixkids.domain.usecase.organization.UpdateClassNameUseCase
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "ClassSettingViewModel_D107"
@HiltViewModel
class ClassSettingViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val loadSelectedOrganizationNameUseCase: LoadSelectedOrganizationNameUseCase,
    private val saveSelectedOrganizationNameUseCase: SaveSelectedOrganizationNameUseCase,
    private val updateClassNameUseCase: UpdateClassNameUseCase
) : BaseViewModel<ClassSettingState, ClassSettingEffect>(ClassSettingState()) {

    private var organizationId: Int? = null
    fun onSchoolNameChanged(schoolName: String) { intent { copy(schoolName = schoolName) } }
    fun onGradeChanged(grade: String) {
        intent { copy(grade = if (grade.isBlank()) null else grade.toInt()) }
    }
    fun onClassNumberChanged(classNumber: String) {
        intent { copy(classNumber = if (classNumber.isBlank()) null else classNumber.toInt()) }
    }

    fun loadSelectedOrganizationName() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }

            loadSelectedOrganizationNameUseCase()
                .onSuccess {
                    intent { copy(classString = it.replace("\n"," ")) }
                    if (it.isNotBlank()){
                        separateClassString(it)
                    } else {
                        postSideEffect(ClassSettingEffect.onShowSnackBar("학급 정보를 불러오는데 실패했습니다. ;("))
                        postSideEffect(ClassSettingEffect.navigateBack)
                    }
                }
                .onFailure {
                    postSideEffect(ClassSettingEffect.onShowSnackBar("학급 정보를 불러오는데 실패했습니다. ;("))
                }

            intent { copy(isLoading = false) }
        }
    }

    fun updateClassName(){
        if (currentState.schoolName.isBlank() || currentState.grade == null || currentState.classNumber == null) {
            postSideEffect(ClassSettingEffect.onShowSnackBar("학급 정보를 입력해주세요."))
            return
        }
        viewModelScope.launch {

            if (organizationId == null) {
                getOrganizationId()
            }

            if (organizationId == null) {
                Log.d(TAG, "updateClassName: $organizationId")
                postSideEffect(ClassSettingEffect.onShowSnackBar("학급 정보를 불러오는데 실패했습니다. ;("))
                return@launch
            } else {
                val updateClassString = "${currentState.schoolName}\n${currentState.grade}학년 ${currentState.classNumber}반"
                updateClassNameUseCase(
                    organizationId!!,
                    updateClassString
                ).onSuccess {
                    saveSelectedOrganizationNameUseCase(updateClassString)
                    postSideEffect(ClassSettingEffect.onShowSnackBar("학급 정보를 업데이트했습니다. :)"))
                    postSideEffect(ClassSettingEffect.navigateBack)
                }.onFailure {
                    postSideEffect(ClassSettingEffect.onShowSnackBar("학급 정보를 업데이트하는데 실패했습니다. ;("))
                }
            }
        }
    }

    private fun separateClassString(classString: String){
        Log.d(TAG, "separateClassString: $classString")
        var school_name = ""
        var grade = 0
        var classNumber = 0
        school_name = classString.split("\n")[0]
        grade = classString.split("\n")[1].split("학년")[0].toInt()
        classNumber = classString.split("\n")[1].split(" ")[1].split("반")[0].toInt()

        intent { copy(schoolName = school_name, grade = grade, classNumber = classNumber) }
    }

    private suspend fun getOrganizationId(){
            getSelectedOrganizationIdUseCase()
                .onSuccess {
                    organizationId = it
                }
    }
}