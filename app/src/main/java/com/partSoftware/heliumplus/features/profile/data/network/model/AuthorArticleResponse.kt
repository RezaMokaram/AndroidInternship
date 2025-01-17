package com.partSoftware.heliumplus.features.profile.data.network.model


import com.google.gson.annotations.SerializedName
import com.partSoftware.heliumplus.features.article.data.network.medel.TagResponse

data class AuthorArticleResponse(
    @SerializedName("author_id")
    val authorId: Int?,
    @SerializedName("comments_count")
    val commentsCount: String?,
    val content: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    val id: Int?,
    @SerializedName("read_time_minutes")
    val readTimeMinutes: String?,
    val tags: List<TagResponse?>?,
    val title: String?
)