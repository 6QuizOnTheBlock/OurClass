package com.sixkids.feature.signin.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sixkids.feature.signin.login.LoginRoute


fun NavController.navigateSignIn() {
    navigate(SignInRoute.defaultRoute)
}

fun NavController.navigateSignUp() {
    navigate(SignInRoute.signUpRoute)
}


fun NavGraphBuilder.signInNavGraph(
    navigateToSignUp: () -> Unit
) {
    composable(route = SignInRoute.defaultRoute){
        LoginRoute(
            navigateToSignUp = navigateToSignUp,
        )
    }
}

object SignInRoute{
    const val defaultRoute = "signIn"
    const val signUpRoute = "signUp"

}