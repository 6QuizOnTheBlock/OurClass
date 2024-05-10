package com.sixkids.student.group.join

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun JoinGroupRoute() {
    JoinGroupScreen()
}

@Composable
fun JoinGroupScreen(
    modifier: Modifier = Modifier
) {
    Text(text = "Join Group Screen")
}
//
//@Preview(showBackground = true)
//@Composable
//fun MemberItemPreview() {
//    MemberItem()
//}
//@Composable
//fun MultiLayeredCircles() {
//    Canvas(modifier = Modifier ){
//        val strokeWidth = 2.dp.toPx()
//        val radiusIncrement = 46.dp.toPx() // 각 원의 반지름 증가량
//
//        val colors = listOf(Red, Orange, Green, Blue) // 원의 색상 리스트
//
//        for (i in colors.indices) {
//            drawCircle(
//                color = colors[i],
//                radius = radiusIncrement * (i + 1),
//                style = Stroke(width = strokeWidth)
//            )
//        }
//    }
//}


@Preview(showBackground = true)
@Composable
fun JoinGroupScreenPreview() {
    JoinGroupScreen()
}

//@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
//@Composable
//fun UlbanCirclePreview() {
//    UlbanTheme {
//        MultiLayeredCircles()
//    }
//}
