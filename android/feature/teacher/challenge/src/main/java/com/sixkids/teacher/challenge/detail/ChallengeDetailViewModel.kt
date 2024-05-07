package com.sixkids.teacher.challenge.detail

import androidx.lifecycle.SavedStateHandle
import com.sixkids.domain.usecase.challenge.GetChallengeDetailUseCase
import com.sixkids.model.AcceptStatus
import com.sixkids.model.ChallengeDetail
import com.sixkids.model.Group
import com.sixkids.model.MemberSimple
import com.sixkids.model.Report
import com.sixkids.teacher.challenge.navigation.ChallengeRoute.CHALLENGE_ID_NAME
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailViewModel @Inject constructor(
    private val getChallengeDetailUseCase: GetChallengeDetailUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ChallengeDetailState, ChallengeDetailSideEffect>(
    ChallengeDetailState()
) {
    private val challengeId = savedStateHandle.get<Int>(CHALLENGE_ID_NAME)!!


    init {
        intent {
            copy(challengeDetail = ChallengeDetail(
                title = "4월 22일 함께 달리기$challengeId",
                content = "문화의 날을 맞아 우리반 친구들 3명이상 만나서 영화를 보자!",
                startTime = LocalDateTime.now(),
                endTime = LocalDateTime.now(),
                reportList = List(10) {
                    Report(
                        content = "4명 다 모여서 쿵푸팬더 4 다같이 봤어요!!",
                        startTime = LocalDateTime.now(),
                        endTime = LocalDateTime.now(),
                        acceptStatus = when (it % 3) {
                            0 -> AcceptStatus.BEFORE
                            1 -> AcceptStatus.APPROVE
                            else -> AcceptStatus.REFUSE
                        },
                        file = "https://file2.nocutnews.co.kr/newsroom/image/2024/04/05/202404052218304873_0.jpg",
                        group = Group(
                            leaderId = 1,
                            studentList = listOf(
                                MemberSimple(
                                    id = 1,
                                    name = "김규리",
                                    photo = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSGfpQ3m-QWiXgCBJJbrcUFdNdWAhj7rcUqjeNUC6eKcXZDAtWm"
                                ),
                                MemberSimple(
                                    id = 2,
                                    name = "오하빈",
                                    photo = "https://health.chosun.com/site/data/img_dir/2023/07/17/2023071701753_0.jpg"
                                ),
                                MemberSimple(
                                    id = 3,
                                    name = "차성원",
                                    photo = "https://ichef.bbci.co.uk/ace/ws/800/cpsprodpb/E172/production/_126241775_getty_cats.png"
                                ),
                                MemberSimple(
                                    id = 4,
                                    name = "정철주",
                                    photo = "https://image.newsis.com/2023/07/12/NISI20230712_0001313626_web.jpg?rnd=20230712163021"
                                )
                            )
                        )
                    )
                }
            ))
        }
    }
}
