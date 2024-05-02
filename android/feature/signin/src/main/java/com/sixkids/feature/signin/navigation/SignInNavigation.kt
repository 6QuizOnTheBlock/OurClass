package com.sixkids.feature.signin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sixkids.feature.signin.login.LoginRoute
import com.sixkids.feature.signin.signup.SignUpPhotoRoute
import com.sixkids.feature.signin.signup.SignUpRoute


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
    navigateToHome: () -> Unit
) {
    composable(route = SignInRoute.defaultRoute){
        LoginRoute(
            navigateToSignUp = navigateToSignUp,
            navigateToHome = navigateToHome
        )
    }

    composable(route = SignInRoute.signUpRoute){
        SignUpRoute(
            navigateToSignUpPhoto = { isTeacher ->
                navigateSignUpPhoto(isTeacher)
            }
        )
    }

    composable(
        route = SignInRoute.signUpPhotoRoute,
        arguments = listOf(navArgument(SignInRoute.SIGN_UP_TEACHER) { type = NavType.BoolType })
    ){
        SignUpPhotoRoute(
            navigateToHome = navigateToHome
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