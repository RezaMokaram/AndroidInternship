package com.partSoftware.heliumplus.features.authenticate.ui.model


import com.partSoftware.heliumplus.features.authenticate.data.network.model.UserInfoResponse

data class UserInfoView(
    val id: Int?,
    val firstName: String?,
    val lastName: String?,
)

fun UserInfoResponse.toUserInfoView() = UserInfoView(
    id = id,
    firstName = firstName,
    lastName = lastName
)