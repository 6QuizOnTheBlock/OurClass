package com.sixkids.designsystem.component.button

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.BlueDark
import com.sixkids.designsystem.theme.Gray
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun UlbanFilledButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = UlbanTypography.titleSmall.copy(fontSize = 14.sp),
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(12.dp),
    color: Color = Blue,
    elevation: Dp = 4.dp,
    textColor: Color = BlueDark,
    disabledTextColor: Color = Gray,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = elevation
        ),
        enabled = enabled
    ) {
        Text(
            text = text, color = if (enabled) textColor else disabledTextColor,
            style = textStyle,
            modifier = Modifier.padding(4.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun UlbanFilledButtonPreview() {
    UlbanTheme {
        UlbanFilledButton(
            text = "Button",
            onClick = {},
        )
    }
}
