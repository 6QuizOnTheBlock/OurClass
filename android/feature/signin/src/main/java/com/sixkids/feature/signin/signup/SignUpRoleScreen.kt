package com.sixkids.feature.signin.signup

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography


@Composable
fun SignUpRoleScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(21.dp)
    ) {
        TopSection()
        BodySection(onCardClick = {}, Modifier.weight(1f).padding(0.dp, 0.dp, 0.dp, 40.dp))
    }
}

@Composable
fun TopSection() {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        Image(
            painter = painterResource(id = com.sixkids.designsystem.R.drawable.ic_arrow_back),
            contentDescription = "back button",
        )

        Text(
            text = "어떤 회원으로 가입할까요?",
            style = UlbanTypography.titleMedium,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
fun BodySection(onCardClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = com.sixkids.designsystem.theme.Red
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
        ) {
            Row(modifier = Modifier
                .padding(40.dp, 10.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start) {

                Image(
                    painter = painterResource(id = com.sixkids.designsystem.R.drawable.teacher_woman),
                    contentDescription = "role image",
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
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = com.sixkids.designsystem.theme.Green
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
        ) {
            Row(modifier = Modifier
                .padding(40.dp, 10.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End) {
                Text(
                    text = "학생",
                    style = UlbanTypography.titleLarge.copy(
                        fontSize = 28.sp
                    ),
                )

                Spacer(modifier = Modifier.size(10.dp))

                Image(
                    painter = painterResource(id = com.sixkids.designsystem.R.drawable.student_girl),
                    contentDescription = "role image",
                    modifier = Modifier.size(120.dp)
                )

            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewSignUpRoleScreen() {
    UlbanTheme {
        SignUpRoleScreen()
    }
}

