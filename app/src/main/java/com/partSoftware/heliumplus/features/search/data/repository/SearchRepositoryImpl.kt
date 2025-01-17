package com.partSoftware.heliumplus.features.search.data.repository

import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.core.networkUtils.safeGetData
import com.partSoftware.heliumplus.features.article.data.db.entity.toArticleEntity
import com.partSoftware.heliumplus.features.article.ui.model.ArticleView
import com.partSoftware.heliumplus.features.article.ui.model.toArticleView
import com.partSoftware.heliumplus.features.search.data.dataSource.local.SearchLocalDataSource
import com.partSoftware.heliumplus.features.search.data.dataSource.remote.SearchRemoteDataSource
import com.partSoftware.heliumplus.features.search.ui.model.*
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val searchLocalDataSource: SearchLocalDataSource
) :
    SearchRepository {

    override suspend fun getTags(searchRequestView: SearchRequestView):
            ResultWrapper<List<TagViewSearch>?> = safeGetData {
        searchRemoteDataSource.getSearchResult(searchRequestView.toSearchRequest())
            ?.tags?.map { it.toTagVewSearch() }
    }

    override suspend fun getUsers(searchRequestView: SearchRequestView):
            ResultWrapper<List<UsersViewSearch>?> = safeGetData {
        searchRemoteDataSource.getSearchResult(searchRequestView.toSearchRequest())
            ?.users?.map { it.toUsersViewSearch() }
    }

    override suspend fun getArticles(searchRequestView: SearchRequestView)
            : ResultWrapper<List<ArticleView>?> {

        val response = safeGetData {
            searchRemoteDataSource.getSearchResult(searchRequestView.toSearchRequest())
                ?.articles?.map { it.toArticleView() }
        }

        if (response is ResultWrapper.Success) {
            val remoteArticles =
                response.data?.map { it.toArticleEntity() } ?: emptyList()

            searchLocalDataSource.insertArticles(remoteArticles)
        }

        return response
    }
}