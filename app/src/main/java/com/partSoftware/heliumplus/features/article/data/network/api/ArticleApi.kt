package com.partSoftware.heliumplus.features.article.data.network.api

import com.partSoftware.heliumplus.features.article.data.network.medel.AddArticleResponse
import com.partSoftware.heliumplus.features.article.data.network.medel.AddArticleRequest
import com.partSoftware.heliumplus.features.article.data.network.medel.ArticlesResponse
import com.partSoftware.heliumplus.features.article.data.network.medel.TagDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ArticleApi {

    @GET("tags")
    suspend fun getTags(): Response<TagDataResponse>

    @GET("articles")
    suspend fun getArticles(): Response<ArticlesResponse>

    @GET("articles")
    suspend fun getArticlesByTags(@Query("tags")tagsIds:String)
    : Response<ArticlesResponse>

    @POST("articles")
    suspend fun addArticle(@Body addArticleRequest: AddArticleRequest): Response<AddArticleResponse>
}