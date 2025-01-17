package com.partSoftware.heliumplus.features.profile.data.datasource.remote

import com.partSoftware.heliumplus.core.common.bodyOrThrow
import com.partSoftware.heliumplus.features.profile.data.network.api.ProfileApi
import com.partSoftware.heliumplus.features.profile.data.network.model.AuthorArticleResponse
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(private val profileApi: ProfileApi) :
    ProfileRemoteDataSource {

    override suspend fun getAuthorArticles(authorId: Int): List<AuthorArticleResponse?>? =
        profileApi.getAuthorArticles(authorId).bodyOrThrow()?.data
}