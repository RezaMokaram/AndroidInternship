package com.partSoftware.heliumplus.core.networkUtils

import android.content.SharedPreferences
import com.partSoftware.heliumplus.core.data.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(private val sharedPreferences: SharedPreferences) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", sharedPreferences.getString(Constants.USER_TOKEN, "") ?: "")
            .build()
        return chain.proceed(request)
    }
}