package com.sixkids.data.network

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val wrapperType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (getRawType(returnType) != ApiResult::class.java) {
            return null
        }

        val bodyType = getParameterUpperBound(0, wrapperType as ParameterizedType)
        return ApiResultCallAdapter<Any>(bodyType)
    }
}