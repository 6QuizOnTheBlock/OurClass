package com.sixkids.designsystem.component.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.RedDark
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun UlbanSnackbar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    message: String,
    @DrawableRes actionIconId: Int? = null,
    actionButtonText: String? = null,
    onClickActionButton: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            UlbanSnackbarContent(
                modifier = Modifier,
                message = message,
                actionIconId = actionIconId,
                actionButtonText = actionButtonText,
                onClickActionButton = onClickActionButton,
            )
        }
    }
}

@Composable
private fun UlbanSnackbarContent(
    modifier: Modifier = Modifier,
    message: String,
    @DrawableRes actionIconId: Int? = null,
    actionButtonText: String? = null,
    onClickActionButton: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(color = Cream, shape = RoundedCornerShape(4.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = modifier.weight(1f), text = message,
            style = UlbanTypography.bodyMedium
        )

        if (actionIconId != null) {
            Icon(
                painter = painterResource(id = actionIconId),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClickActionButton() },
                tint = RedDark
            )
        }

        if (actionButtonText != null) {
            Text(
                modifier = Modifier
                    .clickable(
                        onClick = onClickActionButton,
                    ),
                text = actionButtonText,
                style = UlbanTypography.bodySmall.copy(color = RedDark)
            )
        }
    }
}


@Preview
@Composable
fun UlbanSnackbarPreview() {
    UlbanTheme {
        Column {
            UlbanSnackbarContent(message = "This is a snackbar")
            UlbanSnackbarContent(message = "This is a snackbar This is a snackbar This is a snackbar ")

            UlbanSnackbarContent(message = "This is a snackbar", actionButtonText = "Action")

            UlbanSnackbarContent(
                message = "This is a snackbar",
                actionIconId = R.drawable.ic_arrow_back
            )

            UlbanSnackbarContent(
                message = "This is a snackbar This is a snackbar This is a snackbar ",
                actionButtonText = "Action"
            )

            UlbanSnackbarContent(
                message = "This is a snackbar This is a snackbar This is a snackbar ",
                actionIconId = R.drawable.ic_arrow_back
            )
        }
    }
}
