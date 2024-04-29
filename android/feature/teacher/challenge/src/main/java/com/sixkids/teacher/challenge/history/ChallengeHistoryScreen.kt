package com.sixkids.teacher.challenge.history

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sixkids.designsystem.theme.UlbanTheme

@Composable
fun ChallengeHistoryScreen(
    padding: PaddingValues,
    viewModel: ChallengeHistoryViewModel = hiltViewModel()
) {

}

@Preview(showBackground = true)
@Composable
fun MyPageDefaultScreenPreview() {
    UlbanTheme {
        ChallengeHistoryScreen(padding = PaddingValues(0.dp))
    }
}
