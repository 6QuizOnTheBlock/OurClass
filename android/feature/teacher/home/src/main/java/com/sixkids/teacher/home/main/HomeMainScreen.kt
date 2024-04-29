package com.sixkids.teacher.home.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.UlbanTheme

@Composable
fun HomeMainRoute(
    padding: PaddingValues
){
    HomeMainScreen(padding)
}

@Composable
fun HomeMainScreen(
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Home Main Screen",
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@Preview(showBackground = true)
fun HomeMainScreenPreview() {
    UlbanTheme {
        HomeMainScreen(PaddingValues(0.dp))
    }
}