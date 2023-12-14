package com.partSoftware.heliumplus.features.authenticate.ui.model

import com.partSoftware.heliumplus.features.authenticate.data.network.model.LoginResponse

data class LoginResponseView(
    val accessToken: String?,
)

fun LoginResponse.toLoginResponseView() =
    LoginResponseView(
        accessToken = accessToken,
    )