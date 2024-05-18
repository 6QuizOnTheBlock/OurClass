package com.sixkids.teacher.manageclass.chattingfilter

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sixkids.domain.usecase.chatting.CreateNewChattingFilterWordUseCase
import com.sixkids.domain.usecase.chatting.DeleteChattingFilterWordUseCase
import com.sixkids.domain.usecase.chatting.GetChattingFilterWordsUseCase
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.model.ChatFilterWord
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChattingFilterViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val getChattingFilterWordsUseCase: GetChattingFilterWordsUseCase,
    private val deleteChattingFilterWordUseCase: DeleteChattingFilterWordUseCase,
    private val createNewChattingFilterWordUseCase: CreateNewChattingFilterWordUseCase
): BaseViewModel<ChattingFilterState, ChattingFilterEffect>(
    ChattingFilterState()
){
    private var organizationId: Int? = null

    var chattingFilterWords: Flow<PagingData<ChatFilterWord>>? = null

    fun getChatFilterWords(){
        viewModelScope.launch {
            intent { copy(isLoading = true) }

            if (organizationId == null){
                organizationId = getSelectedOrganizationIdUseCase().getOrNull()
            }

            if (organizationId != null){
                chattingFilterWords =
                    getChattingFilterWordsUseCase(
                        organizationId!!
                    ).cachedIn(viewModelScope)
            } else {
                postSideEffect(ChattingFilterEffect.onShowSnackBar("학급 정보를 불러오지 못했어요 ;("))
            }

            intent { copy(isLoading = false) }
        }
    }

    fun deleteChattingFilterWord(chatFilterWord: ChatFilterWord){
        viewModelScope.launch {
            intent { copy(isLoading = true) }

            deleteChattingFilterWordUseCase(chatFilterWord.id)

            postSideEffect(ChattingFilterEffect.refreshChattingFilterWords)

            intent { copy(isLoading = false) }
        }
    }

    fun newChattingFilter(word: String){
        viewModelScope.launch {
            if (organizationId == null){
                organizationId = getSelectedOrganizationIdUseCase().getOrNull()
            }

            if (organizationId != null){
                createNewChattingFilterWordUseCase(
                    organizationId!!.toLong(),
                    word
                ).onSuccess {
                    postSideEffect(ChattingFilterEffect.refreshChattingFilterWords)
                }.onFailure {
                    postSideEffect(ChattingFilterEffect.onShowSnackBar("필터링 단어 등록에 실패했어요"))
                }
                hideDialog()
                postSideEffect(ChattingFilterEffect.refreshChattingFilterWords)
            } else {
                postSideEffect(ChattingFilterEffect.onShowSnackBar("학급 정보를 불러오지 못했어요 ;("))
            }
        }
    }


    fun hideDialog(){ intent { copy(isShowDialog = false) }
        Log.d("TAG", "hideDialog: ")}
    fun showDialog(){ intent { copy(isShowDialog = true) } }

}