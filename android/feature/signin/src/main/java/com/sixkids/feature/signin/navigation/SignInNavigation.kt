package com.sixkids.feature.signin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sixkids.feature.signin.login.LoginRoute
import com.sixkids.feature.signin.signup.SignUpRoute


fun NavController.navigateSignIn() {
    navigate(SignInRoute.defaultRoute)
}

fun NavController.navigateSignUp() {
    navigate(SignInRoute.signUpRoute)
}


fun NavGraphBuilder.signInNavGraph(
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit
) {
    composable(route = SignInRoute.defaultRoute){
        LoginRoute(
            navigateToSignUp = navigateToSignUp,
            navigateToHome = navigateToHome
        )
    }

    composable(route = SignInRoute.signUpRoute){
        SignUpRoute {
            navigateToHome()

        }
    }
}

object SignInRoute{
    const val defaultRoute = "signIn"
    const val signUpRoute = "signUp"

}