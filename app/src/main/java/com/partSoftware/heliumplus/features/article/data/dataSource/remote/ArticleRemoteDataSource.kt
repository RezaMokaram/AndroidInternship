package com.partSoftware.heliumplus.features.article.data.dataSource.remote

import com.partSoftware.heliumplus.features.article.data.network.medel.*

interface ArticleRemoteDataSource {

    suspend fun fetchTags(): List<TagResponse>?

    suspend fun getArticles(): List<ArticleResponse?>?

    suspend fun getArticlesByTags(tagsIds:String): List<ArticleResponse?>?

    suspend fun addArticle(addArticleRequest: AddArticleRequest): AddArticleResponse?
}