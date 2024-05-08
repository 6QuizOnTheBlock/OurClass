package com.sixkids.teacher.board.chatting

import com.sixkids.model.Chat
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChattingViewModel @Inject constructor(

) : BaseViewModel<ChattingState, ChattingSideEffect>(ChattingState()){
    private val tmpChatList = mutableListOf<Chat>(
        Chat(
            id = "1",
            roomId = 1,
            memberId = 3,
            memberName = TMP_NAME_3,
            memberProfilePhoto = TMP_IMAGE_3,
            content = "안녕하세요 장원영입니다. 만나서 반갑습니다. 잘 부탁드립니다.",
            sendDateTime = System.currentTimeMillis()
        ),
        Chat(
            id = "2",
            roomId = 1,
            memberId = 2,
            memberName = TMP_NAME_2,
            memberProfilePhoto = TMP_IMAGE_2,
            content = "안녕하세요 저는 윈터입니다. 만나서 반갑습니다. 저도 잘 부탁드립니다.",
            sendDateTime = System.currentTimeMillis()
        ),Chat(
            id = "3",
            roomId = 1,
            memberId = 1,
            memberName = TMP_NAME_1,
            memberProfilePhoto = TMP_IMAGE_1,
            content = "그래, 원영이 윈터 반갑고~",
            sendDateTime = System.currentTimeMillis()
        ),Chat(
            id = "4",
            roomId = 1,
            memberId = 1,
            memberName = TMP_NAME_1,
            memberProfilePhoto = TMP_IMAGE_1,
            content = "오빠도 잘 부탁한다 얘들아 ㅋㅋ",
            sendDateTime = System.currentTimeMillis()
        ),Chat(
            id = "5",
            roomId = 1,
            memberId = 3,
            memberName = TMP_NAME_3,
            memberProfilePhoto = TMP_IMAGE_3,
            content = "오빠 내일 뭐해?",
            sendDateTime = System.currentTimeMillis()
        ),Chat(
            id = "6",
            roomId = 1,
            memberId = 3,
            memberName = TMP_NAME_3,
            memberProfilePhoto = TMP_IMAGE_3,
            content = "내일 싸피 끝나고 술 한 잔 할래?",
            sendDateTime = System.currentTimeMillis()
        ),Chat(
            id = "7",
            roomId = 1,
            memberId = 1,
            memberName = TMP_NAME_1,
            memberProfilePhoto = TMP_IMAGE_1,
            content = "오빠는 언제든지 콜이야~",
            sendDateTime = System.currentTimeMillis()
        ),Chat(
            id = "8",
            roomId = 1,
            memberId = 2,
            memberName = TMP_NAME_2,
            memberProfilePhoto = TMP_IMAGE_2,
            content = "헐 오빠 저도 같이 마시면 안돼요?",
            sendDateTime = System.currentTimeMillis()
        ),
        Chat(
            id = "8",
            roomId = 1,
            memberId = 2,
            memberName = TMP_NAME_2,
            memberProfilePhoto = TMP_IMAGE_2,
            content = "제발요 ㅠㅠ",
            sendDateTime = System.currentTimeMillis()
        ),
    )

    fun initChatData(){
        intent { copy(chatList = tmpChatList) }
    }

    fun updateMessage(message: String){
        intent { copy(message = message) }
    }

    fun sendMessage(message: String){
        tmpChatList.add(
            Chat(
                id = "${tmpChatList.size + 1}",
                roomId = 1,
                memberId = 1,
                memberName = TMP_NAME_1,
                memberProfilePhoto = TMP_IMAGE_1,
                content = message,
                sendDateTime = System.currentTimeMillis()
            )
        )
        intent { copy(chatList = tmpChatList, message = "") }
    }

    companion object{
        private const val TMP_IMAGE_1 = "https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/d39f2842-3ad1-4471-8a2a-4ef8cb20951f_profile.jpg"
        private const val TMP_IMAGE_2 = "https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/87058e2e-b7e3-4061-bf8a-8641a338f6d5_profile.jpg"
        private const val TMP_IMAGE_3 = "https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/58658948-0ccf-41f5-b61a-90608e09229f_profile.jpg"

        private const val TMP_NAME_1 = "홍유준"
        private const val TMP_NAME_2 = "윈터"
        private const val TMP_NAME_3 = "장원영"

        private const val TMP_ID_1 = 1
        private const val TMP_ID_2 = 2
        private const val TMP_ID_3 = 3

    }
}