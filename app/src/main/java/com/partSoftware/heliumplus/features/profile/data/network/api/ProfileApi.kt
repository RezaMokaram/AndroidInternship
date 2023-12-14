package com.partSoftware.heliumplus.features.profile.data.network.api

import com.partSoftware.heliumplus.features.profile.data.network.model.AuthorArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApi {

    @GET("articles")
    suspend fun getAuthorArticles(@Query("author_id") authorId: Int): Response<AuthorArticlesResponse>
}