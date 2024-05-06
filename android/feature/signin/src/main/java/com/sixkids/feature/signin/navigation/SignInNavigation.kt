package com.sixkids.feature.signin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sixkids.feature.signin.login.LoginRoute
import com.sixkids.feature.signin.signup.SignUpPhotoRoute
import com.sixkids.feature.signin.signup.SignUpRoute
import com.sixkids.ui.SnackbarToken


fun NavController.navigateSignIn() {
    navigate(SignInRoute.defaultRoute)
}

fun NavController.navigateSignUp() {
    navigate(SignInRoute.signUpRoute)
}

fun NavController.navigateSignUpPhoto(isTeacher: Boolean) {
    navigate(SignInRoute.signUpPhotoRoute(isTeacher))
}


fun NavGraphBuilder.signInNavGraph(
    navigateToSignUp: () -> Unit,
    navigateSignUpPhoto: (Boolean) -> Unit,
    navigateToHome: () -> Unit,
    navigateToTeacherOrganizationList: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    onBackClick : () -> Unit
) {
    composable(route = SignInRoute.defaultRoute){
        LoginRoute(
            navigateToSignUp = navigateToSignUp,
            navigateToHome = navigateToHome,
            onShowSnackBar = onShowSnackBar,
            navigateToTeacherOrganizationList = navigateToTeacherOrganizationList
        )
    }

    composable(route = SignInRoute.signUpRoute){
        SignUpRoute(
            navigateToSignUpPhoto = { isTeacher ->
                navigateSignUpPhoto(isTeacher)
            },
            onBackClick = onBackClick
        )
    }

    composable(
        route = SignInRoute.signUpPhotoRoute,
        arguments = listOf(navArgument(SignInRoute.SIGN_UP_TEACHER) { type = NavType.BoolType })
    ){
        SignUpPhotoRoute(
            onShowSnackBar = onShowSnackBar,
            navigateToTeacherOrganizationList = navigateToTeacherOrganizationList,
            onBackClick = onBackClick
        )
    }
}

object SignInRoute{
    const val SIGN_UP_TEACHER = "isTeacher"

    const val defaultRoute = "signIn"
    const val signUpRoute = "signUp"
    const val signUpPhotoRoute = "sign-up-photo/{$SIGN_UP_TEACHER}"

    fun signUpPhotoRoute(isTeacher: Boolean) = "sign-up-photo/$isTeacher"

}