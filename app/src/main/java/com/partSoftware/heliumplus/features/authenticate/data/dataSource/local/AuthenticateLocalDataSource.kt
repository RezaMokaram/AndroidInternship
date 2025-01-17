package com.partSoftware.heliumplus.features.authenticate.data.dataSource.local

import com.partSoftware.heliumplus.features.authenticate.data.network.model.UserInfoResponse

interface AuthenticateLocalDataSource {

    fun getUserToken(): String

    fun saveUserToken(token: String)

    fun saveUserInfo(userInfo: UserInfoResponse)

    fun getUserInfo(): UserInfoResponse
}