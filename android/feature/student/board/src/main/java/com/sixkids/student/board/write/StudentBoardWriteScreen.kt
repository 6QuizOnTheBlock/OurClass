package com.sixkids.student.board.write

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.R as UlbanRes
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.student.board.R
import com.sixkids.student.board.component.PageTitle
import com.sixkids.ui.SnackbarToken
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun StudentBoardWriteRoute(
    viewModel: StudentBoardWriteViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateBack: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val photoLauncher = rememberLauncherForActivityResult(
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
                viewModel.onAddPhoto(bitmap)
            } catch (e: IOException) {
                viewModel.showToast("사진 호출에 실패했습니다.")
            }
        }
    }

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                StudentBoardWriteEffect.NavigateBack -> navigateBack()
                is StudentBoardWriteEffect.OnShowSnackbar -> {
                    onShowSnackBar(SnackbarToken(message = sideEffect.message))
                }
            }
        }
    }



    StudentBoardWriteScreen(
        postWriteState = uiState,
        cancelOnClick = { viewModel.onBack() },
        submitOnClick = {
            viewModel.onPost(
                uiState.photo?.let { saveBitmapToFile(context, it, "post_photo.jpg") }
            )
        },
        titleValueChange = { viewModel.onTitleChanged(it) },
        contentValueChange = { viewModel.onContentChanged(it) },
        anonymousCheckedChange = { viewModel.onAnonymousChecked(it) },
        addPhotoOnClick = { photoLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
    )
}

@Composable
fun StudentBoardWriteScreen(
    modifier: Modifier = Modifier,
    postWriteState: StudentBoardWriteState = StudentBoardWriteState(),
    cancelOnClick: () -> Unit = {},
    submitOnClick: () -> Unit = {},
    titleValueChange: (String) -> Unit = {},
    contentValueChange: (String) -> Unit = {},
    anonymousCheckedChange: (Boolean) -> Unit = {},
    addPhotoOnClick: () -> Unit = {}
) {

    val scrollState = rememberScrollState()

    LaunchedEffect(postWriteState.content) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            PageTitle(
                title = stringResource(id = R.string.student_board_write_title),
                cancelOnclick = cancelOnClick
            )
            //title
            OutlinedTextField(
                value = postWriteState.title,
                onValueChange = titleValueChange,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.student_board_write_content_title),
                        style = UlbanTypography.bodyLarge
                    )
                },
                textStyle = UlbanTypography.bodyLarge
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = Color.Black
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                //photo
                if (postWriteState.photo != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        bitmap = postWriteState.photo.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                //content
                OutlinedTextField(
                    value = postWriteState.content,
                    onValueChange = { string ->
                        contentValueChange(string)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.student_board_write_content_content),
                            style = UlbanTypography.bodyLarge
                        )
                    },
                    textStyle = UlbanTypography.bodyLarge
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 이미지 추가 아이콘
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = addPhotoOnClick),
                    imageVector = ImageVector.vectorResource(id = UlbanRes.drawable.ic_photo_camera),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                // 익명 체크박스
                Checkbox(
                    modifier = Modifier.scale(1.2f),
                    checked = postWriteState.anonymousChecked,
                    onCheckedChange = anonymousCheckedChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Blue,
                        uncheckedColor = Color.Black
                    )
                )
                Text(
                    text = stringResource(id = R.string.student_board_write_anonymous),
                    style = UlbanTypography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                // 등록 버튼
                UlbanFilledButton(
                    text = stringResource(id = R.string.student_board_write_submit),
                    onClick = submitOnClick
                )
            }
        }

        if (postWriteState.isLoading) {
            LoadingScreen()
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

@Preview(showBackground = true)
@Composable
fun StudentBoardWriteScreenPreview() {
    StudentBoardWriteScreen()
}