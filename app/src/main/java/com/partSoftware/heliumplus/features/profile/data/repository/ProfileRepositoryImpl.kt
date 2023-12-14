package com.partSoftware.heliumplus.features.profile.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.partSoftware.heliumplus.core.common.NetworkConnectivity
import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.core.networkUtils.safeGetData
import com.partSoftware.heliumplus.features.article.data.db.entity.toArticleEntity
import com.partSoftware.heliumplus.features.article.ui.model.ArticleView
import com.partSoftware.heliumplus.features.article.ui.model.toArticleView
import com.partSoftware.heliumplus.features.authenticate.ui.model.UserInfoView
import com.partSoftware.heliumplus.features.authenticate.ui.model.toUserInfoView
import com.partSoftware.heliumplus.features.profile.data.datasource.local.ProfileLocalDataSource
import com.partSoftware.heliumplus.features.profile.data.datasource.remote.ProfileRemoteDataSource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileLocalDataSource: ProfileLocalDataSource,
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private val networkConnectivity: NetworkConnectivity
) : ProfileRepository {

    override fun getUserInfo(): UserInfoView =
        profileLocalDataSource.getUserInfo().toUserInfoView()

    override suspend fun getAuthorArticles(authorId: Int): ResultWrapper<List<ArticleView>?> {
        if (networkConnectivity.hasNetworkConnection()) {
            when (val response =
                safeGetData { profileRemoteDataSource.getAuthorArticles(authorId) }) {
                is ResultWrapper.Success -> {

                    val remoteAuthorArticles =
                        response.data?.filterNotNull()?.map { it.toArticleEntity() } ?: emptyList()

                    profileLocalDataSource.insertArticles(remoteAuthorArticles)
                }
                is ResultWrapper.Error -> {
                    return response
                }
                is ResultWrapper.Failure -> {
                    return response
                }
            }
        }
        return safeGetData {
            profileLocalDataSource.getAuthorArticles(authorId).map { it.toArticleView() }
        }
    }

    override fun getBookmarkedArticles(): LiveData<List<ArticleView>> =
        profileLocalDataSource.getBookmarkedArticles().map {
            it.map { bookmarkAndArticle -> bookmarkAndArticle.articleEntity.toArticleView()}
        }
}