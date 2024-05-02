package com.sixkids.feature.signin.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.BlueDark
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun SignUpPhotoRoute(
    viewModel: SignUpPhotoViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
){
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is SignUpPhotoEffect.NavigateToHome -> navigateToHome()
        }
    }

    SignUpPhotoScreen(
        uiState = uiState,
        isTeacher = true,
        onClickPhoto = {}
    )

}

@Composable
fun SignUpPhotoScreen(
    uiState: SignUpPhotoState,
    isTeacher: Boolean = true,
    onClickPhoto: (Int) -> Unit
) {
    val image_man = if (isTeacher) R.drawable.teacher_man else R.drawable.student_boy
    val image_woman = if (isTeacher) R.drawable.teacher_woman else R.drawable.student_girl

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(21.dp)
    ) {
        SignUpPhotoTopSection()

        Spacer(modifier = Modifier.height(60.dp))

        SelectedPhotoCard(
            uiState,
            modifier = Modifier
                .padding(10.dp)
                .size(180.dp)
                .align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.height(60.dp))

        Row {
            PhotoCard(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .aspectRatio(1f),
                img = image_man,
                onClickPhoto = onClickPhoto
            )

            PhotoCard(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .aspectRatio(1f),
                img = image_woman,
                onClickPhoto = onClickPhoto
            )

            PhotoCard(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .aspectRatio(1f),
                img =  R.drawable.camera,
                onClickPhoto = onClickPhoto
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        DoneButton()
    }
}

@Composable
fun SignUpPhotoTopSection() {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "back button",
        )

        Text(
            text = "프로필 사진을 선택하세요",
            style = UlbanTypography.titleMedium,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
fun DoneButton() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp), // 둥근 모서리를 가진 버튼
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue
        )
    )
    {
        Text(
            text = "완료",
            style = UlbanTypography.titleSmall.copy(
                fontSize = 14.sp,
                color = BlueDark
            ),
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun SelectedPhotoCard(uiState: SignUpPhotoState, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, pressedElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Cream
        ),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            if(uiState.profileDefaultPhoto != null) {
                Image(
                    painter = painterResource(id = uiState.profileDefaultPhoto),
                    contentDescription = "selected photo",
                    modifier = Modifier,
                )
            }else{
                Image(
                    bitmap = uiState.profileUserPhoto!!.asImageBitmap(),
                    contentDescription = "selected photo",
                    modifier = Modifier,
                )

            }
        }
    }
}

@Composable
fun PhotoCard(modifier: Modifier = Modifier, img: Int, onClickPhoto: (Int) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, pressedElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Cream
        ),
        modifier = modifier.clickable {
            onClickPhoto(img)
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id =img),
                contentDescription = "profile",
                modifier = Modifier,
            )
        }
    }
}