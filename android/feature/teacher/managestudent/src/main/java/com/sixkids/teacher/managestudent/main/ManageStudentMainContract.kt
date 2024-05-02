package com.sixkids.teacher.managestudent.main

data class ManageStudentMainState(
    val isLoading: Boolean = false,
    val classString: String = "",
    val studentList: List<String> = emptyList(), //TODO : 학생 데이터 클래스 생성 후 적용
)