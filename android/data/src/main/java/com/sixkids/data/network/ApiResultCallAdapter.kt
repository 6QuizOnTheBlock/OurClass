package com.sixkids.data.network

import android.util.Log
import com.sixkids.data.R
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

private const val TAG = "ApiResultCallAdapter_hong"

class ApiResultCallAdapter<R>(
    private val successType: Type
) : CallAdapter<R, Call<ApiResult<R>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<R>): Call<ApiResult<R>> =
        ApiResultCall(call, successType)
}

private class ApiResultCall<R>(
    private val delegate: Call<R>,
    private val successType: Type,
) : Call<ApiResult<R>> {

    override fun enqueue(callback: Callback<ApiResult<R>>) {
        delegate.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@ApiResultCall, Response.success(response.toApiResult()))
            }

            private fun Response<R>.toApiResult(): ApiResult<R> {
                if (!isSuccessful) { // Http 응답 에러
                    val errorBody = errorBody()?.string()
                    return ApiResult.Failure.HttpError(
                        code = code(),
                        message = message(),
                        body = errorBody ?: "Unknown http response error",
                    )
                }

                body()?.let { body -> return ApiResult.successOf(body) }

                return if (successType == Unit::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    (ApiResult.successOf(Unit as R))
                } else {
                    Log.d(
                        TAG,
                        "toApiResult: successType이 Unit이 아닌 값입니다. 확인하세요. successType: $successType"
                    )
                    ApiResult.Failure.UnknownApiError(
                        IllegalStateException(
                            "successType이 Unit이 아닌 값입니다. 확인하세요. successType: $successType",
                        ),
                    )
                }
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                val error = if (throwable is IOException) {
                    ApiResult.Failure.NetworkError(throwable)
                } else {
                    ApiResult.Failure.UnknownApiError(throwable)
                }
                callback.onResponse(this@ApiResultCall, Response.success(error))
            }
        })
    }

    override fun clone(): Call<ApiResult<R>> = ApiResultCall(delegate.clone(), successType)

    override fun execute(): Response<ApiResult<R>> {
        val response = delegate.execute()
        return if (response.isSuccessful && response.body() != null) {
            Response.success(ApiResult.Success(response.body()!!))
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun cancel() = delegate.cancel()
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()

}