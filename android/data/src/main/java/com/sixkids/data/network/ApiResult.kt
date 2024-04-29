package com.sixkids.data.network

import com.sixkids.model.BadRequestException
import com.sixkids.model.ForbiddenException
import com.sixkids.model.NetworkException
import com.sixkids.model.NotFoundException
import com.sixkids.model.UnknownException

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>

    sealed interface Failure : ApiResult<Nothing> {
        data class HttpError(val code: Int, val message: String, val body: String) : Failure
        data class NetworkError(val throwable: Throwable) : Failure
        data class UnknownApiError(val throwable: Throwable) : Failure

        fun safeThrowable(): Throwable =
            when (this) {
                is HttpError -> handleHttpError(this)
                is NetworkError -> throwable
                is UnknownApiError -> throwable
            }
    }

    val isSuccess : Boolean
        get() = this is Success<T>

    val isFailure : Boolean
        get() = this is Error

    fun getOrNull() : T? =
        when(this) {
            is Success -> data
            else -> null
        }

    fun getOrThrow() : T {
        throwFailure()
        return (this as Success).data
    }

    companion object {
        fun <R> successOf(result: R): ApiResult<R> = Success(result)
    }
}

internal fun ApiResult<*>.throwFailure() {
    if (this is ApiResult.Failure) {
        throw safeThrowable()
    }
}

private fun handleHttpError(httpError: ApiResult.Failure.HttpError): Exception = when(httpError.code) {
    400 -> BadRequestException()
    403 -> ForbiddenException()
    404 -> NotFoundException()
    500, 501, 502, 503, 504, 505 -> NetworkException()
    else -> UnknownException()
}