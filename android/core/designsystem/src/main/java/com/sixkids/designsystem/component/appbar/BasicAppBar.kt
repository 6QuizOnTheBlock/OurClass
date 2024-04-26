package com.sixkids.designsystem.component.appbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.AppBarTypography

@Composable
fun BasicAppBar(
    modifier: Modifier = Modifier,
    leftIcon: @Composable () -> Unit,
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

    Box {
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically(
                animationSpec = tween(durationMillis = 300)
            ),
            exit = slideOutVertically(
                animationSpec = tween(durationMillis = 300)
            )
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable { onclick() }
                    .background(
                        color = color,
                        shape = RoundedCornerShape(bottomEnd = cornerRadius)
                    )
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                leftIcon()
                content()
            }
        }
        if (expanded.not()) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        color = color,
                        shape = RoundedCornerShape(bottomEnd = cornerRadius)
                    ),
            ) {
                Text(
                    modifier = modifier
                        .align(Alignment.Center),
                    text = title, style = AppBarTypography.titleSmall
                )
            }
        }
    }
}
