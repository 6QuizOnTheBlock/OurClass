package com.sixkids.feature.signin.signup

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.R
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.component.screen.UlbanTopSection
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.BlueDark
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private const val TAG = "D107"

@Composable
fun SignUpPhotoRoute(
    viewModel: SignUpPhotoViewModel = hiltViewModel(),
    navigateToTeacherOrganizationList: () -> Unit,
    navigateToStudentOrganizationList: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            try {
                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, it))
                }
                viewModel.onProfilePhotoSelected(bitmap)
            } catch (e: IOException) {
                Log.e(TAG, "Error decoding bitmap", e)
            }
        }
    }

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is SignUpPhotoEffect.OnShowSnackBar -> onShowSnackBar(it.tkn)
            SignUpPhotoEffect.NavigateToTeacherOrganizationList -> navigateToTeacherOrganizationList()
            SignUpPhotoEffect.NavigateToStudentOrganizationList -> navigateToStudentOrganizationList()
        }
    }

    SignUpPhotoScreen(
        uiState = uiState,
        isTeacher = viewModel.isTeacher,
        onClickPhoto = { resId ->
            when(resId){
                R.drawable.camera ->
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                R.drawable.teacher_man ->
                    viewModel.onProfileDefaultPhotoSelected(resId, Gender.MAN)
                R.drawable.student_boy ->
                    viewModel.onProfileDefaultPhotoSelected(resId, Gender.MAN)
                R.drawable.teacher_woman ->
                    viewModel.onProfileDefaultPhotoSelected(resId, Gender.WOMAN)
                R.drawable.student_girl ->
                    viewModel.onProfileDefaultPhotoSelected(resId, Gender.WOMAN)
            }
        },
        onDoneClick = {
            viewModel.signUp(
                saveBitmapToFile(context, uiState.profileUserPhoto, "profile.jpg")
            )
        },
        onBackClick = {
            onBackClick()
        }
    )
}
@Composable
fun SignUpPhotoScreen(
    uiState: SignUpPhotoState = SignUpPhotoState(),
    isTeacher: Boolean = true,
    onClickPhoto: (Int) -> Unit = {},
    onDoneClick : () -> Unit = {},
    onBackClick : () -> Unit = {}
) {
    val imageMan = if (isTeacher) R.drawable.teacher_man else R.drawable.student_boy
    val imageWoman = if (isTeacher) R.drawable.teacher_woman else R.drawable.student_girl
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(21.dp)
        ) {
            UlbanTopSection(stringResource(id = com.sixkids.signin.R.string.signup_photo_title), onBackClick)

            Spacer(modifier = Modifier.height(60.dp))

            SelectedPhotoCard(
                uiState.profileDefaultPhoto ?: imageMan,
                uiState.profileUserPhoto,
                modifier = Modifier
                    .padding(10.dp)
                    .size(180.dp)
                    .align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(60.dp))

            Row {
                PhotoCard(
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                        .aspectRatio(1f),
                    img = imageMan,
                    onClickPhoto = onClickPhoto
                )

                PhotoCard(
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                        .aspectRatio(1f),
                    img = imageWoman,
                    onClickPhoto = onClickPhoto
                )

                PhotoCard(
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                        .aspectRatio(1f),
                    img = R.drawable.camera,
                    onClickPhoto = onClickPhoto
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            DoneButton(onDoneClick)
        }
        if (uiState.isLoading){
            LoadingScreen()
        }
    }
}

@Composable
fun DoneButton(
    onDoneClick: () -> Unit
) {
    Button(
        onClick = { onDoneClick() },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue
        )
    )
    {
        Text(
            text = "완료",
            style = UlbanTypography.titleSmall.copy(
                fontSize = 14.sp,
                color = BlueDark
            ),
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun SelectedPhotoCard(defaultImage: Int, bitmap: Bitmap?, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, pressedElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Cream
        ),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "selected photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(
                        id = defaultImage
                    ),
                    contentDescription = "selected photo",
                    modifier = Modifier.fillMaxSize(),
                )
            }

        }
    }
}

@Composable
fun PhotoCard(modifier: Modifier = Modifier, img: Int, onClickPhoto: (Int) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, pressedElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Cream
        ),
        modifier = modifier.clickable {
            onClickPhoto(img)
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = img),
                contentDescription = "profile",
                modifier = Modifier,
            )
        }
    }
}

fun saveBitmapToFile(context: Context, bitmap: Bitmap?, fileName: String): File? {
    val directory = context.getExternalFilesDir(null) ?: return null

    val file = File(directory, fileName)
    var fileOutputStream: FileOutputStream? = null

    try {
        fileOutputStream = FileOutputStream(file)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.flush()
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    } finally {
        try {
            fileOutputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return file
}

@Composable
@Preview(showBackground = true)
fun SignUpPhotoScreenPreview() {
    UlbanTheme {
        SignUpPhotoScreen()
    }
}