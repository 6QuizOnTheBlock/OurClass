package com.sixkids.student.group.create

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CreateGroupRoute() {
    CreateGroupScreen()
}

@Composable
fun CreateGroupScreen() {
    Text(text = "Create Group Screen")
}

@Preview(showBackground = true)
@Composable
fun CreateGroupScreenPreview() {
    CreateGroupScreen()
}
