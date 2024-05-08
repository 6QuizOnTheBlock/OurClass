package com.sixkids.student.board.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StudentHomeMainRoute(
    padding: PaddingValues
){
    Box(
        modifier = Modifier.padding(padding)
    ){
        StudentHomeMainScreen()
    }
}

@Composable
fun StudentHomeMainScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Student Home Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun StudentHomeMainScreenPreview() {
    StudentHomeMainScreen()
}