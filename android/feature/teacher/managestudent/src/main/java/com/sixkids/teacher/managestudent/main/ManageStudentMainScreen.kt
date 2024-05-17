package com.sixkids.teacher.managestudent.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.item.StudentSimpleCardItem
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.MemberSimple
import com.sixkids.teacher.managestudent.R
import com.sixkids.designsystem.R as UlbanRes


@Composable
fun ManageStudentMainRoute(
    padding: PaddingValues,
    viewModel: ManageStudentMainViewModel = hiltViewModel(),
    navigateToStudentDetail: (Long) -> Unit,

) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = viewModel) {
        viewModel.initData()
    }

    Box(
        modifier = Modifier.padding(padding)
    ) {
        ManageStudentMainScreen(
            uiState = uiState
        )
    }
}

@Composable
fun ManageStudentMainScreen(
    modifier: Modifier = Modifier,
    uiState: ManageStudentMainState = ManageStudentMainState()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.manage_student_title),
            style = UlbanTypography.titleLarge
        )
        Text(
            text = uiState.classString,
            style = UlbanTypography.bodySmall
        )
        Spacer(modifier = Modifier.height(20.dp))

        // 학생 리스트
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
        ) {
            items(uiState.studentList.size) {
                StudentSimpleCardItem(
                    modifier = Modifier.padding(4.dp),
                    name = uiState.studentList[it].name,
                    photo = uiState.studentList[it].photo,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ManageStudentMainScreenPreview() {
    ManageStudentMainScreen()
}