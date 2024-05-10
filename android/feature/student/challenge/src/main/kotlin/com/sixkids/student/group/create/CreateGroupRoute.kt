package com.sixkids.student.group.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CreateGroupRoute() {
    CreateGroupScreen()
}

@Composable
fun CreateGroupScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun CreateGroupScreenPreview() {
    CreateGroupScreen()
}
