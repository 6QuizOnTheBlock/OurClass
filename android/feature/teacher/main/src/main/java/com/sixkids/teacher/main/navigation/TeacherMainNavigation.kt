package com.sixkids.teacher.main.navigation

import androidx.navigation.NavController

fun NavController.navigateClassList() {
    navigate(TeacherMainRoute.defaultRoute)
}

fun NavController.navigateNewClass() {
    navigate(TeacherMainRoute.newClassRoute)
}

fun NavController.navigateProfile() {
    navigate(TeacherMainRoute.profileRoute)
}

object TeacherMainRoute{
    const val defaultRoute = "class-list"
    const val newClassRoute = "new-class"
    const val profileRoute = "profile"
}