package com.sixkids.student.group.create

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import com.sixkids.model.MemberSimple
import com.sixkids.student.challenge.R
import com.sixkids.student.group.component.GroupWaiting
import com.sixkids.student.group.component.MemberIcon
import com.sixkids.student.group.component.MemberIconItem
import com.sixkids.student.group.component.MultiLayeredCircles
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun CreateGroupRoute(
    viewModel: CreateGroupViewModel = hiltViewModel(),
    navigateToChallengeHistory: () -> Unit,
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
            is CreateGroupEffect.NavigateToChallengeHistory -> navigateToChallengeHistory()
            is CreateGroupEffect.HandleException -> handleException(it.throwable, it.retryAction)
        }
    }
    CreateGroupScreen(
        uiState = uiState,
        onMemberSelect = viewModel::selectMember,
        onMemberRemove = viewModel::removeMember,
        onGroupCreate = viewModel::createGroup
    )
}

@Composable
fun CreateGroupScreen(
    uiState: CreateGroupState = CreateGroupState(),
    onMemberSelect: (MemberSimple) -> Unit = { },
    onMemberRemove: (Long) -> Unit = { },
    onGroupCreate: () -> Unit = { }
) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.invite_friend),
                style = UlbanTypography.titleSmall,
                modifier = Modifier.padding(top = 32.dp)
            )
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                MultiLayeredCircles(modifier = Modifier.align(Alignment.Center))
                MemberIcon(
                    memberIconItem = MemberIconItem(
                        member = uiState.leader,
                        isActive = true
                    ), modifier = Modifier.align(Alignment.Center)
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    val member0 = uiState.showMembers[0]
                    val member1 = uiState.showMembers[1]
                    val member2 = uiState.showMembers[2]
                    val member3 = uiState.showMembers[3]
                    val member4 = uiState.showMembers[4]
                    if (member0 != null) {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Box(modifier = Modifier.offset((-100).dp, (-100).dp)) {
                                MemberIcon(
                                    memberIconItem = MemberIconItem(
                                        member = member0,
                                        isActive = true
                                    ), modifier = Modifier.align(Alignment.Center),
                                    onIconClick = { onMemberSelect(member0) }
                                )
                            }
                        }
                    }
                    if (member1 != null) {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Box(modifier = Modifier.offset((-135).dp, (70).dp)) {
                                MemberIcon(
                                    memberIconItem = MemberIconItem(
                                        member = member1,
                                        isActive = true
                                    ), modifier = Modifier.align(Alignment.Center),
                                    onIconClick = { onMemberSelect(member1) }
                                )
                            }
                        }
                    }

                    if (member2 != null) {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Box(modifier = Modifier.offset((120).dp, (60).dp)) {
                                MemberIcon(
                                    memberIconItem = MemberIconItem(
                                        member = member2,
                                        isActive = true
                                    ), modifier = Modifier.align(Alignment.Center),
                                    onIconClick = { onMemberSelect(member2) }
                                )
                            }
                        }
                    }

                    if (member3 != null) {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Box(modifier = Modifier.offset((80).dp, (-120).dp)) {
                                MemberIcon(
                                    memberIconItem = MemberIconItem(
                                        member = member3,
                                        isActive = true
                                    ), modifier = Modifier.align(Alignment.Center),
                                    onIconClick = { onMemberSelect(member3) }
                                )
                            }
                        }
                    }

                    if (member4 != null) {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Box(modifier = Modifier.offset((80).dp, (-120).dp)) {
                                MemberIcon(
                                    memberIconItem = MemberIconItem(
                                        member = member4,
                                        isActive = true
                                    ), modifier = Modifier.align(Alignment.Center),
                                    onIconClick = { onMemberSelect(member4) }
                                )
                            }
                        }
                    }
                }
            }

            GroupWaiting(
                groupSize = uiState.groupSize,
                leader = uiState.leader,
                memberList = uiState.selectedMembers,
                waitingMemberList = uiState.waitingMembers,
                onRemoveClick = {
                    onMemberRemove(it)
                },
                onDoneClick = onGroupCreate
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateGroupScreenPreview() {
    UlbanTheme {
        CreateGroupScreen()
    }
}
