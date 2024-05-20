package com.sixkids.student.group.component

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.Green
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTheme

@Composable
fun MultiLayeredCircles(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier ){
        val strokeWidth = 2.dp.toPx()
        val radiusIncrement = 46.dp.toPx() // 각 원의 반지름 증가량

        val colors = listOf(Red, Orange, Green, Blue) // 원의 색상 리스트

        for (i in colors.indices) {
            drawCircle(
                color = colors[i],
                radius = radiusIncrement * (i + 1),
                style = Stroke(width = strokeWidth)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MultiLayeredCirclesPreview() {
    UlbanTheme {
        MultiLayeredCircles()
    }
}
