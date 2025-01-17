package com.partSoftware.heliumplus.features.search.data.network.model


import com.google.gson.annotations.SerializedName

data class UserResponseSearch(
    val bio: Any?,
    @SerializedName("first_name")
    val firstName: String?,
    val id: Int?,
    @SerializedName("last_name")
    val lastName: String?
)