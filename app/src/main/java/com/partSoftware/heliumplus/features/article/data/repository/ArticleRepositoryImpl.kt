package com.partSoftware.heliumplus.features.article.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.partSoftware.heliumplus.core.common.NetworkConnectivity
import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.core.networkUtils.safeGetData
import com.partSoftware.heliumplus.features.article.data.dataSource.local.ArticleLocalDataSource
import com.partSoftware.heliumplus.features.article.data.dataSource.remote.ArticleRemoteDataSource
import com.partSoftware.heliumplus.features.article.data.db.entity.toArticleEntity
import com.partSoftware.heliumplus.features.article.data.db.entity.toTagEntity
import com.partSoftware.heliumplus.features.article.data.network.medel.toArticleRequest
import com.partSoftware.heliumplus.features.article.ui.model.*
import com.partSoftware.heliumplus.features.profile.data.db.BookmarkEntity
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleLocalDataSource: ArticleLocalDataSource,
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val networkConnectivity: NetworkConnectivity
) : ArticleRepository {

    override suspend fun fetchTags(): ResultWrapper<List<TagView>?>? {
        if (networkConnectivity.hasNetworkConnection()) {
            when (val response = safeGetData { articleRemoteDataSource.fetchTags() }) {
                is ResultWrapper.Success -> {

                    val selectedTagsIds = articleLocalDataSource.getSelectedTagsIds()
                    val checkedTagsIds = articleLocalDataSource.getCheckedTagsIds()

                    articleLocalDataSource.deleteTags()
                    val remoteTags = response.data?.map { it.toTagEntity() } ?: emptyList()

                    remoteTags.map {

                        if (it.id in selectedTagsIds)
                            it.isSelected = true
                        if (it.id in checkedTagsIds)
                            it.isChecked = true
                    }

                    articleLocalDataSource.insertTags(remoteTags)

                }
                is ResultWrapper.Error -> {
                    return response
                }
                is ResultWrapper.Failure -> {
                    return response
                }
            }

        }
        return null
    }

    override fun getTags(): LiveData<List<TagView>?>? =
        articleLocalDataSource.getTags()
            ?.map { it?.map { articleEntity -> articleEntity.toTagView() } }

    override fun getSelectedTags(): LiveData<List<TagView>?>? =
        articleLocalDataSource.getTags()
            ?.map { tags ->
                tags?.map { articleEntity -> articleEntity.toTagView() }?.filter {
                    it.isSelected
                }
            }

    override suspend fun updateTags(tags: List<TagView>) =
        articleLocalDataSource.updateTags(tags.map { it.toTagEntity() })

    override suspend fun getArticles(): ResultWrapper<List<ArticleView>?> {
        if (networkConnectivity.hasNetworkConnection()) {
            when (val response = safeGetData { articleRemoteDataSource.getArticles() }) {
                is ResultWrapper.Success -> {

                    val remoteArticles =
                        response.data?.filterNotNull()?.map { it.toArticleEntity() } ?: emptyList()

                    articleLocalDataSource.insertArticles(remoteArticles)
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
            articleLocalDataSource.getArticles().map { it.toArticleView() }
        }
    }

    override suspend fun getArticle(id: Int): ArticleView =
        articleLocalDataSource.getArticle(id).toArticleView()

    override suspend fun getArticlesByTag(checkedTagsIds: List<Int>):
            ResultWrapper<List<ArticleView>?> {
        if (networkConnectivity.hasNetworkConnection()) {
            when (val response =
                safeGetData {
                    articleRemoteDataSource.getArticlesByTags(
                        checkedTagsIds
                            .toString().drop(1).dropLast(1).replace(" ", "")
                    )
                }) {
                is ResultWrapper.Success -> {

                    val remoteArticles =
                        response.data?.filterNotNull()?.map { it.toArticleEntity() } ?: emptyList()

                    articleLocalDataSource.insertArticles(remoteArticles)
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
            articleLocalDataSource
                .getArticlesByTag(checkedTagsIds)
                .map { it.toArticleView() }
        }
    }

    override suspend fun addArticle(addArticleRequestView: AddArticleRequestView):
            ResultWrapper<AddArticleResponseView?> =
        safeGetData {
            articleRemoteDataSource.addArticle(addArticleRequestView.toArticleRequest())
                ?.toAddArticleResponseView()
        }

    override suspend fun insertBookmarkEntity(articleId: Int?) =
        articleLocalDataSource.insertBookmarkEntity(BookmarkEntity(articleId))

    override fun isBookmarked(articleId: Int?): Boolean =
        articleLocalDataSource.isBookmarked(articleId)

    override suspend fun deleteBookmarkEntity(articleId: Int) =
        articleLocalDataSource.deleteBookmarkEntity(BookmarkEntity(articleId))

    override fun getCheckedTagsIds(): List<Int> =
        articleLocalDataSource.getCheckedTagsIds()

    override fun getCheckedTagsIdsLiveData(): LiveData<List<Int>?>? =
        articleLocalDataSource.getCheckedTagsIdsLiveData()
}