package com.partSoftware.heliumplus.features.article.data.network.medel

import com.google.gson.annotations.SerializedName

data class AddArticleResponse(
    @SerializedName("article_id")
    val articleId: Int?,
    val message: String?
)