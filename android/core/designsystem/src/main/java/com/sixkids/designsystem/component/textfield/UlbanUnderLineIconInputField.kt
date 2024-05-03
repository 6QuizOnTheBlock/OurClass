package com.sixkids.designsystem.component.textfield

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.ui.util.formatToDayMonthYear
import java.time.LocalDate

@Composable
fun UlbanUnderLineIconInputField(
    modifier: Modifier = Modifier,
    text: String = "",
    onIconClick: () -> Unit = {},
    @DrawableRes iconResource: Int,
    textStyle: TextStyle = UlbanTypography.bodyMedium
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = textStyle,
                modifier = modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            Icon(
                painter = painterResource(iconResource),
                contentDescription = null,
                modifier = Modifier
                    .clickable { onIconClick() }
                    .padding(horizontal = 8.dp)
                    .size(24.dp)
            )
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Blue,
            thickness = 2.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UlbanUnderLineDateFieldPreview() {
    val date by remember { mutableStateOf(LocalDate.now()) }

    Column {
        Text(
            text = "날짜를 선택해 주세요",
            style = UlbanTypography.titleSmall,
            modifier = Modifier.padding(8.dp)
        )
        UlbanUnderLineIconInputField(
            text = date.formatToDayMonthYear(),
            iconResource = R.drawable.ic_calendar,
        )

    }
}
