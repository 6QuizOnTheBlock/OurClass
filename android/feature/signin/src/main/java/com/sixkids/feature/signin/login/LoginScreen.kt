package com.sixkids.feature.signin.login

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.signin.R
import com.sixkids.ui.extension.collectWithLifecycle
import kotlinx.coroutines.delay

private const val TAG = "HONG"
@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
//    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    val context = LocalContext.current

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val transitionState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is LoginEffect.NavigateToSignUp -> navigateToSignUp()
//            LoginEffect.NavigateToHome -> navigateToHome()
        }
    }

    LoginScreen(
        uiState = uiState,
        onLoginClick = {
            KakaoManager.login(
                context = context,
                onSuccess = { token ->
                    viewModel.login("1", "1")
                },
                onFailed = {
                    Log.d(TAG, "LoginRoute: ${it?.message}")
                    Exception()
                }
            )
        }

    )
}

@Composable
fun LoginScreen(
    uiState: LoginState = LoginState(),
    onLoginClick: () -> Unit = {},
) {
    var animationStart by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        delay(300)
        animationStart = true
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // 배경색을 설정합니다. 실제 색상 코드로 대체해야 합니다.
    ) {
        // 배경 이미지가 전체 화면 너비를 채우고 적절한 비율로 높이가 설정되도록 조정합니다.
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Home Background",
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )

        // Arrange the images in a row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
        ) {
            // Column for the rocket and paint images

            AnimatedVisibility(
                visible = animationStart,
                enter = slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(1000)
                ),
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    AsyncImage(
                        model = com.sixkids.designsystem.R.drawable.hifive,
                        contentDescription = "Rocket",
                        modifier = Modifier.fillMaxWidth(),

                        )

                    AsyncImage(
                        model = com.sixkids.designsystem.R.drawable.paint,
                        contentDescription = "Paint",
                        modifier = Modifier
                            .padding(50.dp, 0.dp, 0.dp, 0.dp)
                            .rotate(15f)
                    )
                }
            }


            // AnimatedVisibility for pencil image
            AnimatedVisibility(
                visible = animationStart,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(1000)
                ),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.Top),
            ) {
                AsyncImage(
                    model = com.sixkids.designsystem.R.drawable.pencil,
                    contentDescription = "Pencil",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)  // 높이를 300dp로 증가시켜 이미지를 더 크게 표시합니다.
                        .rotate(18f)
                )
            }
        }

        // 텍스트와 버튼을 중첩시키기 위해 Column 사용
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f)) // 상단 내용과 "울반" 텍스트 사이의 공간을 추가합니다.

            Text(
                text = "슬기로운 학급생활 메이트",
                style = UlbanTypography.bodyMedium.copy(
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(0.dp, 100.dp, 20.dp, 0.dp)
            )

            Text(
                text = "울반",
                style = UlbanTypography.titleLarge.copy(
                    fontSize = 48.sp
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            LogInButton(onLoginClick)
        }
    }
}

@Composable
fun LogInButton(
    onClick: () -> Unit,
){
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 40.dp)
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFEE500)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.kakao), // Replace with the actual drawable resource ID
                contentDescription = "Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(8.dp)) // Space between icon and text
            Text(
                text = "5초 만에 카카오로 시작하기",
                style = UlbanTypography.bodyMedium.copy(),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    UlbanTheme {
        LoginScreen()
    }

}
