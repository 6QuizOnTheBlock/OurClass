package com.sixkids.data.network

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()

    data class Error(val exception: Throwable) : ApiResult<Nothing>()

    val isSuccess : Boolean
        get() = this is Success<T>

    val isFailure : Boolean
        get() = this is Error

    val getOrNull : T? =
        when(this) {
            is Success -> data
            else -> null
        }
}