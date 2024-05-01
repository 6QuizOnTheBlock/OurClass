package com.sixkids.feature.signin.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun SignUpPhotoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(21.dp)
    ) {
        SignUpPhotoTopSection()

        Spacer(modifier =Modifier.height(60.dp))

        PhotoCard(modifier = Modifier
            .padding(10.dp)
            .size(180.dp)
            .align(Alignment.CenterHorizontally)
            .background(
                color = Red, // 파스텔 푸른색
                shape = RoundedCornerShape(12.dp) // 둥근 모서리
            ),
            img = R.drawable.teacher_woman
        )

        Spacer(modifier =Modifier.height(60.dp))

        Row {
            PhotoCard(modifier = Modifier
                .padding(10.dp)
                .weight(1f)
                .aspectRatio(1f)
                .background(
                    color = Blue, // 파스텔 노란색
                    shape = RoundedCornerShape(12.dp) // 둥근 모서리
                ),
                img = R.drawable.teacher_woman)

            PhotoCard(modifier = Modifier
                .padding(10.dp)
                .weight(1f)
                .aspectRatio(1f)
                .background(
                    color = Blue, // 파스텔 노란색
                    shape = RoundedCornerShape(12.dp) // 둥근 모서리
                ),
                img = R.drawable.teacher_woman)

            PhotoCard(modifier = Modifier
                .padding(10.dp)
                .weight(1f)
                .aspectRatio(1f)
                .background(
                    color = Blue, // 파스텔 노란색
                    shape = RoundedCornerShape(12.dp) // 둥근 모서리
                ),
                img = R.drawable.teacher_woman)
        }

        Spacer(modifier =Modifier.weight(1f))

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
fun DoneButton(){
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp), // 둥근 모서리를 가진 버튼
        colors = ButtonDefaults.buttonColors(
            containerColor = com.sixkids.designsystem.theme.Blue
        )
    )
    {
        Text(
            text ="완료",
            style = UlbanTypography.titleSmall.copy(
                fontSize = 14.sp,
                color = com.sixkids.designsystem.theme.BlueDark
            ),
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun PhotoCard(modifier: Modifier = Modifier, img: Int){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ){
        Image(
            painter = painterResource(id = img),
            contentDescription = "profile",
            modifier = Modifier.fillMaxSize())
    }
}

@Composable
@Preview(showBackground = true)
fun SignUpPhotoTopSectionPreview() {
    UlbanTheme {
        PhotoCard(modifier = Modifier
            .padding(10.dp)
            .size(100.dp)
            .background(
                color = Red, // 파스텔 푸른색
                shape = RoundedCornerShape(12.dp) // 둥근 모서리
            ),
            img = R.drawable.teacher_woman)
    }
}

@Composable
@Preview(showBackground = true)
fun SignUpPhotoScreenPreview() {
    UlbanTheme {
        SignUpPhotoScreen()
    }
}