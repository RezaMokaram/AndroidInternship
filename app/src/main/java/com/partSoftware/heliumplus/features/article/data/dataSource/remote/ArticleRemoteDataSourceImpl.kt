package com.partSoftware.heliumplus.features.article.data.dataSource.remote

import com.partSoftware.heliumplus.core.common.bodyOrThrow
import com.partSoftware.heliumplus.features.article.data.network.api.ArticleApi
import com.partSoftware.heliumplus.features.article.data.network.medel.AddArticleResponse
import com.partSoftware.heliumplus.features.article.data.network.medel.AddArticleRequest
import com.partSoftware.heliumplus.features.article.data.network.medel.ArticleResponse
import com.partSoftware.heliumplus.features.article.data.network.medel.TagResponse
import javax.inject.Inject

class ArticleRemoteDataSourceImpl
@Inject constructor(private val articleApi: ArticleApi) : ArticleRemoteDataSource {

    override suspend fun fetchTags(): List<TagResponse>? =
        articleApi.getTags().bodyOrThrow()?.data

    override suspend fun getArticles(): List<ArticleResponse?>? =
        articleApi.getArticles().bodyOrThrow()?.data

    override suspend fun getArticlesByTags(tagsIds: String): List<ArticleResponse?>? =
        articleApi.getArticlesByTags(tagsIds).bodyOrThrow()?.data

    override suspend fun addArticle(addArticleRequest: AddArticleRequest): AddArticleResponse? =
        articleApi.addArticle(addArticleRequest).bodyOrThrow()
}