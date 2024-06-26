package com.sixkids.feature.signin.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.R
import com.sixkids.designsystem.component.screen.UlbanTopSection
import com.sixkids.designsystem.theme.Green
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun SignUpRoute(
    viewModel: SignUpRoleViewModel = hiltViewModel(),
    navigateToSignUpPhoto: (Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is SignUpRoleEffect.NavigateToSignUpPhoto -> navigateToSignUpPhoto(sideEffect.isTeacher)
        }
    }

    SignUpScreen(
        onTeacherClick = {
            viewModel.onTeacherClick(it)
        },
        onBackClick = {
            onBackClick()
        }
    )
}

@Composable
fun SignUpScreen(
    onTeacherClick: (Boolean) -> Unit = {},
    onBackClick : () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(21.dp)
    ) {
        UlbanTopSection(stringResource(id = com.sixkids.signin.R.string.signup_role_title), onBackClick = onBackClick)
        BodySection(
            onCardClick = onTeacherClick,
            modifier = Modifier
                .weight(1f)
                .padding(0.dp, 0.dp, 0.dp, 40.dp)
        )
    }
}

@Composable
fun BodySection(onCardClick: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .clickable {
                    onCardClick(true)
                },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Red
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
        ) {
            Row(
                modifier = Modifier
                    .padding(40.dp, 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Image(
                    painter = painterResource(id = R.drawable.teacher_woman),
                    contentDescription = "teacher",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "선생님",
                    style = UlbanTypography.titleLarge.copy(
                        fontSize = 28.sp
                    ),
                )

            }
        }

        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .clickable {
                    onCardClick(false)
                },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Green
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
        ) {
            Row(
                modifier = Modifier
                    .padding(40.dp, 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "학생",
                    style = UlbanTypography.titleLarge.copy(
                        fontSize = 28.sp
                    ),
                )

                Spacer(modifier = Modifier.size(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.student_girl),
                    contentDescription = "student",
                    modifier = Modifier.size(120.dp)
                )

            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview() {
    UlbanTheme {
        SignUpScreen()
    }
}