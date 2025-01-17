package com.partSoftware.heliumplus.features.authenticate.data.dataSource.remote

import com.partSoftware.heliumplus.core.common.bodyOrThrow
import com.partSoftware.heliumplus.features.authenticate.data.network.api.AuthenticateApi
import com.partSoftware.heliumplus.features.authenticate.data.network.model.LoginRequest
import com.partSoftware.heliumplus.features.authenticate.data.network.model.LoginResponse
import com.partSoftware.heliumplus.features.authenticate.data.network.model.RegisterRequest
import com.partSoftware.heliumplus.features.authenticate.data.network.model.UserInfoResponse
import javax.inject.Inject

class AuthenticateRemoteDataSourceImpl @Inject
constructor(private val authenticateApi: AuthenticateApi) :
    AuthenticateRemoteDataSource {

    override suspend fun loginUser(loginRequest: LoginRequest): LoginResponse? =
        authenticateApi.loginUser(loginRequest).bodyOrThrow()

    override suspend fun registerUser(registerRequest: RegisterRequest){
        authenticateApi.registerUser(registerRequest).bodyOrThrow()
    }

    override suspend fun getAndSaveUserInfo(): UserInfoResponse? =
        authenticateApi.getUserInfo().bodyOrThrow()
}