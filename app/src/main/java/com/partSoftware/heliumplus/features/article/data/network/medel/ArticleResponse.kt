package com.partSoftware.heliumplus.features.article.data.network.medel

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("author_first_name")
    val authorFirstName: String?,
    @SerializedName("author_id")
    val authorId: Int?,
    @SerializedName("author_last_name")
    val authorLastName: String?,
    @SerializedName("author_username")
    val authorUsername: String?,
    val content: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    val id: Int?,
    @SerializedName("read_time_minutes")
    val readTimeMinutes: String?,
    val tags: List<TagResponse?>?,
    val title: String?
)