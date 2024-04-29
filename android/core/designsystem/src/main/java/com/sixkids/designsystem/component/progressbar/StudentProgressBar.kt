package com.sixkids.designsystem.theme.component.progressbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sixkids.designsystem.theme.RedDark

@Preview(showBackground = true, backgroundColor = 0xFFFFF6F0)
@Composable
fun StudentProgressBarPreview() {
    StudentProgressBar(
        modifier = Modifier.padding(20.dp),
        totalStudentCount = 15,
        successStudentCount = 5
    )
}

@Composable
fun StudentProgressBar(
    modifier: Modifier = Modifier,
    totalStudentCount: Int,
    successStudentCount: Int
){
    Column(
        modifier = modifier,
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            progress = (successStudentCount.toFloat()/totalStudentCount.toFloat()),
            trackColor = Color.White,
            color = RedDark
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "${totalStudentCount}명 중 ${successStudentCount}명이 진행했어요",
            textAlign = TextAlign.End,
            fontSize = 14.sp,
        )
    }
}