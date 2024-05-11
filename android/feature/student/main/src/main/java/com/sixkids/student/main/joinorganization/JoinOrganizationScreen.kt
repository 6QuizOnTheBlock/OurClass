package com.sixkids.student.main.joinorganization

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sixkids.ui.SnackbarToken

@Composable
fun JoinOrganizationRoute(
    viewModel: JoinOrganizationViewModel = hiltViewModel(),
    navigateToStudentOrganizationList: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    onBackClick: () -> Unit
) {

}

@Composable
fun JoinOrganizationScreen(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "학급 가입")
    }
}
