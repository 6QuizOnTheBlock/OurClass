package com.sixkids.teacher.manageclass.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ManageClassMainRoute(
    padding: PaddingValues
){

    Box(
        modifier = Modifier.padding(padding)
    ) {
        ManageClassMainScreen()
    }
}

@Composable
fun ManageClassMainScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun ManageClassMainScreenPreview(){
    ManageClassMainScreen()
}
