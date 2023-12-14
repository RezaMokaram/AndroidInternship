package com.partSoftware.heliumplus.features.search.data.network.model

import com.partSoftware.heliumplus.features.article.data.network.medel.ArticleResponse


data class SearchResponse(
    val articles: List<ArticleResponse>?,
    val tags: List<TagResponseSearch>?,
    val users: List<UserResponseSearch>?
)