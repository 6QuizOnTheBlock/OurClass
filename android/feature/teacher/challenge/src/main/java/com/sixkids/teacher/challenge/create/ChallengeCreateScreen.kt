package com.sixkids.teacher.challenge.create

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ChallengeCreateRoute(
    viewModel: ChallengeCreateViewModel = hiltViewModel()
){

    ChallengeCreateScreen()

}

@Composable
fun ChallengeCreateScreen(){
    Text(text = "Challenge Create Screen")
}
