package com.sixkids.designsystem.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.LoadingBackground

@Composable
fun LoadingScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lodaing))
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        lottieAnimatable.animate(
            composition = composition,
            initialProgress = 0f
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LoadingBackground),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(150.dp).aspectRatio(1f),
            iterations = Int.MAX_VALUE
        )
    }
}
