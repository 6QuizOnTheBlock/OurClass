package com.sixkids.designsystem.component.button

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.Gray

@Composable
fun EditFAB(
    modifier: Modifier = Modifier,
    buttonColor: Color = Cream,
    iconColor: Color = Gray,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        modifier = modifier,
        containerColor = buttonColor,
        onClick = onClick,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
            contentDescription = null,
            tint = iconColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditFABPreview() {
    EditFAB()
}