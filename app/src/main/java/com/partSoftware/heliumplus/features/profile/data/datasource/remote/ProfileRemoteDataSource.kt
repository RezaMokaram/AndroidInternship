package com.partSoftware.heliumplus.features.profile.data.datasource.remote

import com.partSoftware.heliumplus.features.profile.data.network.model.AuthorArticleResponse

interface ProfileRemoteDataSource {

    suspend fun getAuthorArticles(authorId: Int): List<AuthorArticleResponse?>?
}