package com.sixkids.designsystem.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sixkids.designsystem.R

@Composable
@Preview(showBackground = true)
fun TypographyPreview() {
    Column(modifier = Modifier.fillMaxSize()){

        Text(text = "울반 타이포 titleLarge", style = UlbanTypography.titleLarge)

        Text(text = "울반 타이포 titleMedium", style = UlbanTypography.titleMedium)

        Text(text ="울반 타이포 titleSmall", style = UlbanTypography.titleSmall)

        Text(text ="울반 타이포 bodyLarge", style = UlbanTypography.bodyLarge)

        Text(text ="울반 타이포 bodyMedium", style = UlbanTypography.bodyMedium)

        Text(text ="울반 타이포 bodySmall", style = UlbanTypography.bodySmall)

        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "앱바 타이포 titleLarge", style = AppBarTypography.titleLarge)

        Text(text = "앱바 타이포 titleSmall", style = AppBarTypography.titleSmall)

        Text(text = "앱바 타이포 bodyMedium", style = AppBarTypography.bodyMedium)

        Text(text = "앱바 타이포 bodySmall", style = AppBarTypography.bodySmall)

    }
}

val npsFont = FontFamily(
    Font(R.font.nps_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.nps_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.nps_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val UlbanTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = npsFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.8.sp
    ),

    titleMedium = TextStyle(
        fontFamily = npsFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.8.sp
    ),

    titleSmall = TextStyle(
        fontFamily = npsFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.8.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = npsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.8.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = npsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.8.sp
    ),


    bodySmall = TextStyle(
        fontFamily = npsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.8.sp
    ),


)

val AppBarTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = npsFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.8.sp
    ),
    titleSmall = TextStyle(
        fontFamily = npsFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.8.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = npsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.8.sp
    ),

    bodySmall = TextStyle(
        fontFamily = npsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.8.sp
    )

)