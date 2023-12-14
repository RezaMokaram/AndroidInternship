package com.partSoftware.heliumplus.features.profile.data.network.model

data class AuthorArticlesResponse(
    val currentPage: Int?,
    val `data`: List<AuthorArticleResponse?>?,
    val totalPage: Int?
)