package com.sixkids.designsystem.component.appbar

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.AppBarTypography

@Composable
fun BasicAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes leftIcon: Int,
    title: String,
    content: @Composable () -> Unit,
    color: Color,
    expanded: Boolean = true,
    onclick: () -> Unit
) {
    val cornerRadius by animateDpAsState(
        targetValue = if (expanded) 60.dp else 0.dp,
        animationSpec = TweenSpec(durationMillis = 300),
        label = "앱바 코너"
    )

    val animatedHeight by animateDpAsState(
        targetValue = if (expanded) 180.dp else 60.dp,
        animationSpec = TweenSpec(durationMillis = 300),
        label = "앱바 크기"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(animatedHeight),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(bottomEnd = cornerRadius)

    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically(
                initialOffsetY = {
                    -it
                }
            ), exit = fadeOut() + slideOutVertically(
                targetOffsetY = {
                    -it
                }
            )
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable { if (expanded) onclick() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 16.dp)
                        .aspectRatio(1f),
                    painter = painterResource(id = leftIcon),
                    contentDescription = "로고",
                    tint = Color.Unspecified
                )
                content()
            }
        }

        AnimatedVisibility(
            visible = expanded.not(),
            enter = fadeIn() + slideInVertically(
                initialOffsetY = {
                    -it
                }
            ), exit = fadeOut() + slideOutVertically(
                targetOffsetY = {
                    -it
                }
            )
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = modifier
                        .weight(1f)
                )
                Text(
                    modifier = modifier,
                    textAlign = TextAlign.Center,
                    text = title,
                    style = AppBarTypography.titleSmall
                )
                Spacer(
                    modifier = modifier
                        .weight(1f)
                )
            }
        }
    }
}
