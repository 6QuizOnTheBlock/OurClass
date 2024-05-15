package com.sixkids.student.group.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.student.challenge.R
import com.sixkids.student.group.component.GroupWaiting
import com.sixkids.student.group.component.MemberIcon
import com.sixkids.student.group.component.MemberIconItem
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun CreateGroupRoute(
    viewModel: CreateGroupViewModel = hiltViewModel(),
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.connectSse()
        viewModel.createGroupMatchingRoom()
        viewModel.startScan()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.disconnectSse()
            viewModel.stopScan()
        }
    }


    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is CreateGroupEffect.FriendScanStart -> {}
            CreateGroupEffect.FriendScanStop -> {}
            is CreateGroupEffect.HandleException -> handleException(it.throwable, it.retryAction)
        }
    }
    CreateGroupScreen(
        uiState = uiState,
        onMemberSelect = viewModel::selectMember,
        onMemberRemove = viewModel::removeMember
    )
}

@Composable
fun CreateGroupScreen(
    uiState: CreateGroupState = CreateGroupState(),
    onMemberSelect: (MemberIconItem) -> Unit = { },
    onMemberRemove: (Long) -> Unit = { }
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.invite_friend),
            style = UlbanTypography.titleSmall,
            modifier = Modifier.padding(top = 32.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        if (uiState.foundMembers.isNotEmpty()) {
            LazyColumn {
                items(uiState.foundMembers) { member ->
                    MemberIcon(
                        member = member,
                        onIconClick = { onMemberSelect(member) },
                    )
                }
            }
        }

        GroupWaiting(
            groupSize = uiState.groupSize,
            leader = uiState.leader,
            memberList = uiState.selectedMembers,
            onRemoveClick = {
                onMemberRemove(it)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateGroupScreenPreview() {
    UlbanTheme {
        CreateGroupScreen()
    }
}
