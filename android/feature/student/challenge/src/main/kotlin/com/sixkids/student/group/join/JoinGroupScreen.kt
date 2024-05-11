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
