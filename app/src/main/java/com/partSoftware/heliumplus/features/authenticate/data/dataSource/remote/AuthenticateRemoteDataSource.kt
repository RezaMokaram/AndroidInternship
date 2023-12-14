package com.partSoftware.heliumplus.features.authenticate.data.dataSource.remote

import com.partSoftware.heliumplus.features.authenticate.data.network.model.LoginRequest
import com.partSoftware.heliumplus.features.authenticate.data.network.model.LoginResponse
import com.partSoftware.heliumplus.features.authenticate.data.network.model.RegisterRequest
import com.partSoftware.heliumplus.features.authenticate.data.network.model.UserInfoResponse

interface AuthenticateRemoteDataSource {

    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse?

    suspend fun registerUser(registerRequest: RegisterRequest)

    suspend fun getAndSaveUserInfo(): UserInfoResponse?
}