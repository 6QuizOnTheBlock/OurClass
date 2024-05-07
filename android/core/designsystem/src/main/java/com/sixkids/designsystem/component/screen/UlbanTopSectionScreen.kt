package com.sixkids.designsystem.component.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun UlbanTopSection(text: String = "", onBackClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "back button",
            modifier = Modifier.clickable { onBackClick() }
        )

        Text(
            text = text,
            style = UlbanTypography.titleMedium,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}