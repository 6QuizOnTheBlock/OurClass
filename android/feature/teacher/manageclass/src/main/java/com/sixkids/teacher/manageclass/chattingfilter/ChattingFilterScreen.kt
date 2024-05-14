package com.sixkids.teacher.manageclass.chattingfilter

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.button.EditFAB
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.BlueDark
import com.sixkids.designsystem.theme.Yellow
import com.sixkids.designsystem.theme.YellowDark
import com.sixkids.model.ChatFilterWord
import com.sixkids.teacher.manageclass.R
import com.sixkids.teacher.manageclass.chattingfilter.component.ChatFilterWordDialog
import com.sixkids.teacher.manageclass.chattingfilter.component.ChattingFilterItem
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun ChattingFilterRoute(
    viewModel: ChattingFilterViewModel = hiltViewModel(),
    onShowSnackBar: (SnackbarToken) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getChatFilterWords()
    }

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            ChattingFilterEffect.refreshChattingFilterWords -> viewModel.getChatFilterWords()
            is ChattingFilterEffect.onShowSnackBar -> onShowSnackBar(SnackbarToken(message = it.message))
        }
    }

    ChattingFilterScreen(
        chattingFilterState = uiState,
        chattingFilterWordList = viewModel.chattingFilterWords?.collectAsLazyPagingItems(),
        chattingFilterItemDeleteOnClick = { viewModel.deleteChattingFilterWord(it) },
        fabClick = viewModel::showDialog,
        dialogCancelOnClick = viewModel::hideDialog,
        dialogConfirmOnClick = viewModel::newChattingFilter
    )

}

@Composable
fun ChattingFilterScreen(
    modifier: Modifier = Modifier,
    chattingFilterState: ChattingFilterState = ChattingFilterState(),
    chattingFilterWordList: LazyPagingItems<ChatFilterWord>? = null,
    chattingFilterItemDeleteOnClick: (ChatFilterWord) -> Unit = {},
    dialogCancelOnClick: () -> Unit = {},
    dialogConfirmOnClick: (String) -> Unit = {},
    fabClick: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    val backPressState by rememberUpdatedState(newValue = chattingFilterState.isShowDialog)

    BackHandler(enabled = backPressState){
        Log.d("TAG", "ChattingFilterScreen: ")
        dialogCancelOnClick()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column {
            UlbanDetailAppBar(
                leftIcon = UlbanRes.drawable.chat_filter,
                title = stringResource(id = R.string.manage_class_filter_chat),
                content = stringResource(id = R.string.manage_class_filter_chat),
                topDescription = "",
                bottomDescription = "",
                color = Yellow
            )
            if (chattingFilterWordList != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    state = listState,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    items(chattingFilterWordList.itemCount) { index ->
                        chattingFilterWordList[index]?.let {word ->
                            ChattingFilterItem(
                                text = word.badWord,
                                deleteOnclick = { chattingFilterItemDeleteOnClick(word) }
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }

        }
        //FAB
        EditFAB(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            buttonColor = Yellow,
            iconColor = YellowDark,
            onClick = fabClick
        )

        if (chattingFilterState.isLoading){
            LoadingScreen()
        }

        if (chattingFilterState.isShowDialog){
            ChatFilterWordDialog(
                cancelButtonOnClick = dialogCancelOnClick,
                confirmButtonOnClick = dialogConfirmOnClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChattingFilterScreenPreview() {
    ChattingFilterScreen()
}