package com.sixkids.teacher.challenge.create.matching

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.checkbox.TextRadioButton
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.Gray
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.MemberSimple
import com.sixkids.teacher.challenge.R
import com.sixkids.teacher.challenge.create.matching.component.MemberIcon
import com.sixkids.teacher.challenge.create.matching.component.MemberIconItem

@Composable
fun GroupMatchingSettingRoute() {

}

@Composable
fun GroupMatchingSettingScreen(
    modifier: Modifier = Modifier,
    state: GroupMatchingSettingState = GroupMatchingSettingState(),
    onNextButtonClick: () -> Unit = {}
) {
    val radioOptions = MatchingType.entries
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.matching_choice_type),
            style = UlbanTypography.titleSmall
        )
        Spacer(modifier = Modifier.height(20.dp))
        radioOptions.forEach { option ->
            TextRadioButton(
                selected = selectedOption == option,
                onClick = { onOptionSelected(option) },
                text = stringResource(id = option.textRes),
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.matching_choice_student),
                style = UlbanTypography.titleSmall
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${state.studentList.size}명",
                style = UlbanTypography.bodyLarge.copy(
                    color = Gray
                )
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 10.dp),
            color = Blue,
            thickness = 2.dp
        )
        // 학생 목록
        LazyVerticalGrid(
            modifier = Modifier
                .weight(1f),
            columns = GridCells.Fixed(4),
            ) {
            items(state.studentList.size) { index ->
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Cream
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    MemberIcon(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(2.dp),
                        memberIconItem = MemberIconItem(
                            member = state.studentList[index],
                            isActive = true,
                            showX = true,
                        )
                    )
                }
            }
        }
        UlbanFilledButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.next),
            onClick = onNextButtonClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupMatchingSettingScreenPreview() {
    GroupMatchingSettingScreen(
        state = GroupMatchingSettingState(
            studentList = List(30) {
                MemberSimple(
                    id = it.toLong(),
                    name = "학생 $it"
                )
            }
        )
    )
}
