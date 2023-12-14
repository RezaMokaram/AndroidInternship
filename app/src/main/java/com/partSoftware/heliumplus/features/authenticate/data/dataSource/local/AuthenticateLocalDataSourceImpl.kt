package com.partSoftware.heliumplus.features.authenticate.data.dataSource.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.partSoftware.heliumplus.core.data.Constants
import com.partSoftware.heliumplus.features.authenticate.data.network.model.UserInfoResponse
import javax.inject.Inject

class AuthenticateLocalDataSourceImpl
@Inject constructor(private val sharedPreferences: SharedPreferences) :
    AuthenticateLocalDataSource {

    override fun getUserToken(): String =
        sharedPreferences.getString(Constants.USER_TOKEN, "").toString()

    override fun saveUserToken(token: String) =
        sharedPreferences.edit().putString(Constants.USER_TOKEN, token).apply()

    override fun saveUserInfo(userInfo: UserInfoResponse) {
        val userInfoJson = Gson().toJson(userInfo, UserInfoResponse::class.java)
        sharedPreferences.edit().putString(Constants.USER_INFO, userInfoJson).apply()
    }

    override fun getUserInfo(): UserInfoResponse {
        val userInfoJson = sharedPreferences.getString(Constants.USER_INFO, "")
        return Gson().fromJson(userInfoJson, UserInfoResponse::class.java)
    }
}