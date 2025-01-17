package com.partSoftware.heliumplus.features.authenticate.data.network.model


import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("created_at")
    val createdAt: String?,
    val email: String?,
    @SerializedName("first_name")
    val firstName: String?,
    val id: Int?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("phone_number")
    val phoneNumber: String?,
    val username: String?
)