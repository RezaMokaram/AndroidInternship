package com.partSoftware.heliumplus.features.article.data.network.medel

data class ArticlesResponse(
    val currentPage: Int?,
    val `data`: List<ArticleResponse?>?,
    val totalPage: Int?
)