package com.partSoftware.heliumplus.core.networkUtils

import com.partSoftware.heliumplus.core.common.ResultWrapper

suspend fun <T> safeGetData(apiCall: suspend () -> T): ResultWrapper<T> {

    return try {
        ResultWrapper.Success(apiCall.invoke())
    } catch (e: Exception) {
        if (e is ServerException)
            ResultWrapper.Error(e.errorMessage)
        else
            ResultWrapper.Failure("خطای اتصال به سرور")
    }
}