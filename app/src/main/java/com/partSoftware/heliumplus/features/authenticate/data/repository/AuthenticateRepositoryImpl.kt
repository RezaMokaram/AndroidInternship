package com.partSoftware.heliumplus.features.authenticate.data.repository

import com.partSoftware.heliumplus.core.common.NetworkConnectivity
import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.core.networkUtils.safeGetData
import com.partSoftware.heliumplus.features.authenticate.data.dataSource.local.AuthenticateLocalDataSource
import com.partSoftware.heliumplus.features.authenticate.data.dataSource.remote.AuthenticateRemoteDataSource
import com.partSoftware.heliumplus.features.authenticate.data.network.model.toLoginRequest
import com.partSoftware.heliumplus.features.authenticate.data.network.model.toRegisterRequest
import com.partSoftware.heliumplus.features.authenticate.ui.model.*
import javax.inject.Inject

class AuthenticateRepositoryImpl @Inject constructor(
    private val authenticateLocalDataSource: AuthenticateLocalDataSource,
    private val authenticateRemoteDataSource: AuthenticateRemoteDataSource,
    private val networkConnectivity: NetworkConnectivity

) : AuthenticateRepository {


    override fun checkTokenExist() = authenticateLocalDataSource.getUserToken().isNotBlank()

    override fun saveToken(token: String) = authenticateLocalDataSource.saveUserToken(token)

    override suspend fun loginUser(loginRequestView: LoginRequestView)
            : ResultWrapper<LoginResponseView?> =
        safeGetData {
            authenticateRemoteDataSource
                .loginUser(loginRequestView.toLoginRequest())?.toLoginResponseView()
        }

    override suspend fun registerUser(registerRequestView: RegisterRequestView)
            : ResultWrapper<Unit> =
        safeGetData {
            authenticateRemoteDataSource.registerUser(registerRequestView.toRegisterRequest())
        }

    override suspend fun getAndSaveUserInfo(): ResultWrapper<UserInfoView> {

        if (networkConnectivity.hasNetworkConnection()) {
            when (val response = safeGetData { authenticateRemoteDataSource.getAndSaveUserInfo() }) {
                is ResultWrapper.Success -> {
                    response.data?.let { authenticateLocalDataSource.saveUserInfo(it) }
                }
                is ResultWrapper.Error -> {
                    return response
                }
                is ResultWrapper.Failure -> {
                    return response
                }
            }

        }
        return safeGetData {
            authenticateLocalDataSource.getUserInfo().toUserInfoView()
        }
    }
}