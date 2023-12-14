package com.partSoftware.heliumplus.features.authenticate.data.network.model

import com.google.gson.annotations.SerializedName
import com.partSoftware.heliumplus.features.authenticate.ui.model.LoginRequestView

data class LoginRequest(
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: String
)

fun LoginRequestView.toLoginRequest() = LoginRequest(
    password = password,
    phoneNumber = phoneNumber
)