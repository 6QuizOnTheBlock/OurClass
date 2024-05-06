package com.sixkids.teacher.main.profile

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sixkids.designsystem.R
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.RedDark
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private const val TAG = "D107"

@Composable
fun TeacherProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToOrganizationList: () -> Unit,
    navigateToSignIn: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            try {
                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            context.contentResolver,
                            it
                        )
                    )
                }
                viewModel.onProfilePhotoSelected(bitmap)
            } catch (e: IOException) {
                Log.e(TAG, "Error decoding bitmap", e)
            }
        }
    }

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            ProfileEffect.NavigateToOrganizationList -> navigateToOrganizationList()
            is ProfileEffect.OnShowSnackBar -> onShowSnackBar(it.tkn)
            ProfileEffect.NavigateToSignIn -> navigateToSignIn()
        }
    }

    TeacherProfileScreen(
        uiState = uiState,
        onClickPhoto = { resId ->
            when (resId) {
                R.drawable.camera ->
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                R.drawable.teacher_man ->
                    viewModel.onProfileDefaultPhotoSelected(resId, Gender.MAN)

                R.drawable.teacher_woman ->
                    viewModel.onProfileDefaultPhotoSelected(resId, Gender.WOMAN)
            }
        },
        onDoneClick = {
            viewModel.onChangeDoneClick(
                saveBitmapToFile(context, uiState.changedProfileUserPhoto, "profile.jpg")
            )
        },
        onSignOutClick = viewModel::onSignOutClick,
        onBackClick = onBackClick
    )

}

@Composable
fun TeacherProfileScreen(
    uiState: ProfileState = ProfileState(),
    onClickPhoto: (Int) -> Unit = {},
    onDoneClick: () -> Unit = {},
    onSignOutClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val imageMan = R.drawable.teacher_man
    val imageWoman = R.drawable.teacher_woman

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(21.dp)
        ) {
            SignUpPhotoTopSection(uiState.name, onBackClick)

            Spacer(modifier = Modifier.height(60.dp))

            SelectedPhotoCard(
                uiState.changedProfileDefaultPhoto,
                uiState.originalProfilePhoto,
                uiState.changedProfileUserPhoto,
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

            BottomSection(onDoneClick = onDoneClick, onSignOutClick = onSignOutClick)

        }
        if (uiState.isLoading) {
            LoadingScreen()
        }
    }
}

@Composable
fun SignUpPhotoTopSection(name: String, onDoneClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "back button",
            modifier = Modifier.clickable { onDoneClick() }
        )

        Text(
            text = String.format(
                stringResource(id = com.sixkids.teacher.main.R.string.profile_welcome),
                name
            ),
            style = UlbanTypography.titleMedium,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
fun BottomSection(
    onDoneClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onExitClick: () -> Unit = { }
) {
    Column {
        UlbanFilledButton(
            text = stringResource(id = com.sixkids.teacher.main.R.string.profile_done),
            onClick = onDoneClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        UlbanFilledButton(
            text = stringResource(id = com.sixkids.teacher.main.R.string.profile_sign_out),
            onClick = onSignOutClick,
            modifier = Modifier.fillMaxWidth(),
            color = Red,
            textColor = RedDark
        )

        Text(
            text = stringResource(id = com.sixkids.teacher.main.R.string.profile_exit),
            style = UlbanTypography.titleSmall.copy(textDecoration = TextDecoration.Underline),
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { onExitClick() }
        )
    }
}

@Composable
fun SelectedPhotoCard(
    defaultImage: Int?,
    originalImage: String?,
    bitmap: Bitmap?,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Cream
        ),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (originalImage == null && bitmap == null && defaultImage == null) {
                Image(
                    painter = painterResource(
                        id = R.drawable.teacher_man
                    ),
                    contentDescription = "selected photo",
                    modifier = Modifier.fillMaxSize(),
                )

            } else if (originalImage != null) {
                if (bitmap == null && defaultImage == null) {
                    AsyncImage(
                        model = originalImage,
                        contentDescription = "original image",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                } else {
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
                                id = defaultImage!!
                            ),
                            contentDescription = "selected photo",
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoCard(modifier: Modifier = Modifier, img: Int, onClickPhoto: (Int) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
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
    if (bitmap == null) return null
    val file = File(directory, fileName)
    var fileOutputStream: FileOutputStream? = null

    try {
        fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
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
fun TeacherProfileScreenPreview() {
    UlbanTheme {
        TeacherProfileScreen()
    }
}