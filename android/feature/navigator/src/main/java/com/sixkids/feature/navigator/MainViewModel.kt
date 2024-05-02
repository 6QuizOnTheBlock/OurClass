package com.sixkids.feature.navigator

import androidx.lifecycle.viewModelScope
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : BaseViewModel<MainState, MainSideEffect>(MainState()) {
    private val mutex = Mutex()

    fun onShowSnackbar(snackbarToken: SnackbarToken) {
        viewModelScope.launch {
            mutex.withLock {
                intent { copy(snackbarToken = snackbarToken, snackbarVisible = true) }
                delay(SHOW_TOAST_LENGTH)
                intent { copy(snackbarVisible = false) }
            }
        }
    }

    fun onCloseSnackbar() {
        viewModelScope.launch {
            intent { copy(snackbarVisible = false) }
        }
    }

    fun handleException(throwable: Throwable, retry: () -> Unit) {
        onShowSnackbar(SnackbarToken(
            message = throwable.message ?: "알 수 없는 에러 입니다.",
            actionButtonText = "재시도",
            onClickActionButton = retry
        ))
    }


    companion object {
        private const val SHOW_TOAST_LENGTH = 2000L
    }
}
