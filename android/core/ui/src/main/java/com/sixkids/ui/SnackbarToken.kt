package com.sixkids.ui

import androidx.annotation.DrawableRes

data class SnackbarToken(
    val message: String = "",
    @DrawableRes val actionIcon: Int? = null,
    val actionButtonText: String? = null,
    val onClickActionButton: () -> Unit = {},
)
