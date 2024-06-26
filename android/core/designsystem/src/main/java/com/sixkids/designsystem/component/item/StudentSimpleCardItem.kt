package com.sixkids.designsystem.component.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.UlbanTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentSimpleCardItem(
    modifier: Modifier = Modifier,
    id: Long = 0,
    name: String = "",
    photo: String = "",
    score: Int? = null,
    onClick: (Long) -> Unit = {},
    isCount: Boolean = false
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Cream
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        onClick = {onClick(id)}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = photo,

                contentScale = ContentScale.Crop,
                contentDescription = "프로필 사진"
            )
            Text(
                text = name,
                style = UlbanTypography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            if (score != null) {
                Text(
                    text = if (isCount)"${score}회" else "${score}점",
                    style = UlbanTypography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentSimpleCardItemPreview() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        items(25){
            StudentSimpleCardItem(
                modifier = Modifier.padding(4.dp),
                name = "김철수",
//                photo = "https://i.pinimg.com/564x/f9/e5/c1/f9e5c19d2a51bda108e5ea536d7745c1.jpg",
                score = 97
            )
        }
    }
}
