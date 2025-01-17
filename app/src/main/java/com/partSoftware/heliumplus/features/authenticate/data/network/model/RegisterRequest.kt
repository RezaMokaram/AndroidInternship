package com.partSoftware.heliumplus.features.authenticate.data.network.model


import com.google.gson.annotations.SerializedName
import com.partSoftware.heliumplus.features.authenticate.ui.model.RegisterRequestView

data class RegisterRequest(
    val email: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    val password: String?,
    @SerializedName("phone_number")
    val phoneNumber: String?,
    val username: String?
)

fun RegisterRequestView.toRegisterRequest() = RegisterRequest(
    email = email,
    firstName = firstName,
    lastName = lastName,
    password = password,
    phoneNumber = phoneNumber,
    username = username
)