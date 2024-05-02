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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.item.StudentSimpleCardItem
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.managestudent.R
import com.sixkids.designsystem.R as UlbanRes


@Composable
fun ManageStudentMainRoute(
    padding: PaddingValues
) {
    Box(
        modifier = Modifier.padding(padding)
    ) {
        ManageStudentMainScreen(
            //TODO : ManageStudentMainState를 받아오는 로직 추가 후 더미데이터 제거
            manageStudentMainState = ManageStudentMainState(
                classString = "인동초등학교 1학년 1반",
                studentList = listOf("김철수", "이영희", "박영수","김철수", "이영희", "박영수")
            )
        )
    }
}

@Composable
fun ManageStudentMainScreen(
    modifier: Modifier = Modifier,
    manageStudentMainState: ManageStudentMainState = ManageStudentMainState()
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
            text = manageStudentMainState.classString,
            style = UlbanTypography.bodySmall
        )
        Spacer(modifier = Modifier.height(10.dp))
        // 필터 버튼
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Row(
                modifier = Modifier
                    .clickable {

                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "이름순",
                    style = UlbanTypography.bodyLarge,
                )
                Image(
                    imageVector = ImageVector.vectorResource(id = UlbanRes.drawable.ic_filter_alt),
                    contentDescription = null,
                )
            }
        }
        // 학생 리스트
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
        ) {
            items(manageStudentMainState.studentList.size) {
                StudentSimpleCardItem(
                    modifier = Modifier.padding(4.dp),
                    name = manageStudentMainState.studentList[it],
                    photo = "https://i.pinimg.com/564x/f9/e5/c1/f9e5c19d2a51bda108e5ea536d7745c1.jpg",
                    score = 97
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ManageStudentMainScreenPreview() {
    ManageStudentMainScreen(
        manageStudentMainState = ManageStudentMainState(
            classString = "인동초등학교 1학년 1반",
            studentList = listOf("김철수", "이영희", "박영수","김철수", "이영희", "박영수")
        )
    )
}