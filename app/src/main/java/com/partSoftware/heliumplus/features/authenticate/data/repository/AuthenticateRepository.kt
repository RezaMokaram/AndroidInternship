package com.partSoftware.heliumplus.features.authenticate.data.repository

import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.features.authenticate.ui.model.LoginRequestView
import com.partSoftware.heliumplus.features.authenticate.ui.model.LoginResponseView
import com.partSoftware.heliumplus.features.authenticate.ui.model.RegisterRequestView
import com.partSoftware.heliumplus.features.authenticate.ui.model.UserInfoView

interface AuthenticateRepository {

    fun checkTokenExist(): Boolean

    fun saveToken(token: String)

    suspend fun loginUser(loginRequestView: LoginRequestView)
            : ResultWrapper<LoginResponseView?>

    suspend fun registerUser(registerRequestView: RegisterRequestView)
            : ResultWrapper<Unit>

    suspend fun getAndSaveUserInfo(): ResultWrapper<UserInfoView>
}