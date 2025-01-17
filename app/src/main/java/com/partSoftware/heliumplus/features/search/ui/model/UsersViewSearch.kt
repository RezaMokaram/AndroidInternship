package com.partSoftware.heliumplus.features.search.ui.model

import com.google.gson.annotations.SerializedName
import com.partSoftware.heliumplus.features.search.data.network.model.UserResponseSearch

data class UsersViewSearch(
    @SerializedName("bio")
    val bio: Any?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("last_name")
    val lastName: String?
)
{
    fun fullName() = "$firstName $lastName"
}

fun UserResponseSearch.toUsersViewSearch() = UsersViewSearch(
    bio = bio,
    firstName = firstName,
    id = id,
    lastName = lastName
)