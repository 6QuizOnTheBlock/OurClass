package com.sixkids.designsystem.component.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun UlbanMissionCard(
    modifier: Modifier = Modifier,
    title: String = "",
    subTitle: String = "",
    @DrawableRes imgRes: Int = R.drawable.hifive,
    backGroundColor: Color = Red,
    onClick: () -> Unit = {}
) {
    val density = LocalDensity.current
    val cornerRadius = 16.dp // 둥근 모서리의 반경을 dp 단위로 설정
    val heightCut = 0.8f
    val widthCUt = 0.7f

    val customShape = GenericShape { size, _ ->
        with(density) {
            val pxCornerRadius = cornerRadius.toPx()
            moveTo(pxCornerRadius, 0f) // 시작 지점을 둥근 모서리의 반경만큼 오른쪽으로 이동합니다.
            lineTo(size.width - pxCornerRadius, 0f) // 상단 가로선을 그립니다.
            quadraticBezierTo(size.width, 0f, size.width, pxCornerRadius) // 오른쪽 상단 모서리 둥글게 처리
            lineTo(size.width, size.height * heightCut - pxCornerRadius)
            quadraticBezierTo(
                size.width,
                size.height * heightCut,
                size.width - pxCornerRadius,
                size.height * heightCut
            )
            lineTo(size.width * widthCUt + pxCornerRadius, size.height * heightCut)
            quadraticBezierTo(
                size.width * widthCUt,
                size.height * heightCut,
                size.width * widthCUt,
                size.height * heightCut + pxCornerRadius
            )
            lineTo(size.width * widthCUt, size.height - pxCornerRadius)
            quadraticBezierTo(
                size.width * widthCUt,
                size.height,
                size.width * widthCUt - pxCornerRadius,
                size.height
            )
            lineTo(pxCornerRadius, size.height)
            quadraticBezierTo(0f, size.height, 0f, size.height - pxCornerRadius) // 왼쪽 하단 모서리 둥글게 처리
            lineTo(0f, pxCornerRadius) // 왼쪽 세로선을 그립니다.
            quadraticBezierTo(0f, 0f, pxCornerRadius, 0f) // 왼쪽 상단 모서리 둥글게 처리
            close()
        }
    }
    Box(
        modifier = modifier.clickable {
            onClick()
        },
    ) {
        Card(
            shape = customShape,
            colors = CardDefaults.cardColors(
                containerColor = backGroundColor,
            ),
            modifier = Modifier
                .width(200.dp)
                .height(300.dp)
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Column {
                Spacer(modifier = Modifier.height(170.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = UlbanTypography.titleSmall,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(widthCUt),
                        text = subTitle,
                        style = UlbanTypography.titleSmall.copy(fontSize = 12.sp),
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.weight(1 - widthCUt))
                }
            }
        }
        Image(
            modifier = Modifier
                .padding(bottom = 32.dp, end = 32.dp)
                .width(250.dp)
                .height(400.dp)
                .align(Alignment.TopCenter),
            painter = painterResource(id = imgRes),
            contentDescription = null
        )

    }


}

@Preview(showBackground = true)
@Composable
fun UlbanMissionCardPreview() {
    UlbanTheme {
        UlbanMissionCard(
            title = "4월 22일 깜짝 미션",
            subTitle = "상세 정보",
            imgRes = R.drawable.hifive,
            backGroundColor = Red
        )
    }
}
