package com.sixkids.data.network

import okhttp3.Request
import okio.Timeout
import org.json.JSONObject
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.NullPointerException
import java.lang.reflect.Type

class ApiResultCallAdapter<T>(
    private val successType: Type
): CallAdapter<T, Call<ApiResult<T>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<ApiResult<T>> =
        ApiResultCall(call)
}

private class ApiResultCall<T>(
    private val delegate: Call<T>
): Call<ApiResult<T>> {

    override fun enqueue(callback: Callback<ApiResult<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()

                if (response.isSuccessful) {
                    if (body != null) {
                        // 정상적인 response
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(ApiResult.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(ApiResult.Error(NullPointerException("response body is null")))
                        )
                    }
                } else {
                    val errorBodyString = response.errorBody()?.string()
                    val message = try {
                        if (!errorBodyString.isNullOrBlank()) {
                            JSONObject(errorBodyString).getString("message")
                        } else {
                            ""
                        }
                    } catch (e: Exception) {
                        ""
                    }
                    callback.onResponse(
                        this@ApiResultCall,
                        Response.success(ApiResult.Error(UlbanException(response.code(),message)))
                    )
                }

            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val error = if (t is IOException) {
                    ApiResult.Error(IOException("Network error", t))
                } else {
                    ApiResult.Error(t)
                }
                callback.onResponse(this@ApiResultCall, Response.success(error))
            }
        })
    }

    override fun clone(): Call<ApiResult<T>> = ApiResultCall(delegate.clone())

    override fun execute(): Response<ApiResult<T>> {
        val response = delegate.execute()
        return if (response.isSuccessful && response.body() != null ) {
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