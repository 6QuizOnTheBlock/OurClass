package com.sixkids.teacher.managestudent.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.dialog.UlbanBasicDialog
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.StudentRelation

@Composable
fun MemberRelationDialog(
    confirmButtonOnClick: () -> Unit = {},
    relationInfo: StudentRelation = StudentRelation()
) {
    UlbanBasicDialog {
        Column(
            modifier = Modifier.width(280.dp).padding(vertical = 16.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = "${relationInfo.name} 학생과의 기록",
                style = UlbanTypography.titleMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "점수 ${relationInfo.relationPoint}점",
                style = UlbanTypography.bodyMedium
            )
            Text(
                text = "태그 인사 횟수 ${relationInfo.tagGreetingCount}회",
                style = UlbanTypography.bodyMedium
            )
            Text(
                text = "그룹 횟수 ${relationInfo.groupCount}회",
                style = UlbanTypography.bodyMedium
            )
            Text(
                text = "이어 달리기 받은 횟수 ${relationInfo.receiveCount}회",
                style = UlbanTypography.bodyMedium
            )
            Text(
                text = "이어 달리기 전달 횟수 ${relationInfo.sendCount}회",
                style = UlbanTypography.bodyMedium
            )

            UlbanFilledButton(
                modifier = Modifier.fillMaxWidth(),
                text = "확인",
                onClick = { confirmButtonOnClick()})
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MemberRelationDialogPreview() {
    MemberRelationDialog()
}