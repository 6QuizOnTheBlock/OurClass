package com.sixkids.teacher.manageclass.statistics

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.item.StudentSimpleCardItem
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.MemberSimpleClassSummary
import com.sixkids.teacher.manageclass.R
import kotlin.math.max
import kotlin.math.min
import com.sixkids.designsystem.R as DesignSystemR

@Composable
fun StatisticsRoute(
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }

    StatisticsScreen(
        uiState = uiState
    )
}

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    uiState: StatisticsState = StatisticsState(),
) {
    val scrollState = rememberScrollState()
    val isScrolled by remember {
        derivedStateOf {
            scrollState.value > 100
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        UlbanDetailAppBar(
            leftIcon = DesignSystemR.drawable.statistics,
            title = stringResource(id = com.sixkids.teacher.manageclass.R.string.manage_class_statistics),
            content = stringResource(id = com.sixkids.teacher.manageclass.R.string.manage_class_statistics),
            topDescription = "",
            bottomDescription = uiState.organizationName,
            color = Blue,
            expanded = !isScrolled,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp).verticalScroll(scrollState)
        ) {
            BestWorstStudent(
                studentList = uiState.statistics.challengeCounts,
                title = stringResource(id = R.string.statistics_challenge),
                icon = DesignSystemR.drawable.hifive
            )

            BestWorstStudent(
                studentList = uiState.statistics.relayCounts,
                title = stringResource(id = R.string.statistics_relay),
                icon = DesignSystemR.drawable.relay
            )

            BestWorstStudent(
                studentList = uiState.statistics.postsCounts,
                title = stringResource(id = R.string.statistics_post),
                icon = DesignSystemR.drawable.board
            )
        }
    }
}

@Composable
fun BestWorstStudent(
    studentList: List<MemberSimpleClassSummary> = emptyList(),
    title: String = "",
    @DrawableRes icon: Int,

){
    Spacer(modifier = Modifier.height(20.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = icon,
            contentDescription = "relay",
            modifier = Modifier
                .padding(end = 10.dp)
                .size(50.dp)
        )
        Text(text = title, style = UlbanTypography.titleSmall)
    }


    if (studentList.isNotEmpty()) {
        Text(
            text = "가장 많이 참여한 학생",
            style = UlbanTypography.bodyMedium,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (i in 0 until min(3,studentList.size )) {
                StudentSimpleCardItem(
                    modifier = Modifier.weight(1f),
                    id = studentList[i].member.id,
                    name = studentList[i].member.name,
                    photo = studentList[i].member.photo,
                    score = studentList[i].count,
                    isCount = true
                )
            }
        }

        Text(
            text = "가장 적게 참여한 학생",
            style = UlbanTypography.bodyMedium,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        val startIdx = max(studentList.size - 1, 0)
        val endIdx = max(studentList.size - 3, 0)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (i in startIdx downTo  endIdx) {
                StudentSimpleCardItem(
                    modifier = Modifier.weight(1f),
                    id = studentList[i].member.id,
                    name = studentList[i].member.name,
                    photo = studentList[i].member.photo,
                    score = studentList[i].count,
                    isCount = true
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun StatisticsScreenPreview() {
    StatisticsScreen()
}