package com.sixkids.student.home.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sixkids.designsystem.theme.UlbanTypography

@Preview(showBackground = true)
@Composable
fun StudentMainInfoCardPreview() {
    StudentMainInfo(
        name = "홍유준",
        classString = "구미초등학교 1학년 1반",
        exp = 2340,
    )
}

@Composable
fun StudentMainInfo(
    modifier: Modifier = Modifier,
    name: String = "",
    imageUrlString: String = "",
    classString: String = "",
    exp: Int = 0
) {
    val height = 80.dp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row {
            Card {
                AsyncImage(
                    model = imageUrlString,
                    contentDescription = null,
                    modifier = Modifier.size(height),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.height(height),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    textAlign = TextAlign.Start,
                    text = "$name 학생",
                    style = UlbanTypography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    textAlign = TextAlign.Start,
                    text = classString.replace("\n"," "),
                    style = UlbanTypography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    textAlign = TextAlign.Start,
                    text = "${exp}점",
                    style = UlbanTypography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}