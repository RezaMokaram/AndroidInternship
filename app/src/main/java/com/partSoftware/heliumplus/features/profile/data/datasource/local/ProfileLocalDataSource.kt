package com.partSoftware.heliumplus.features.profile.data.datasource.local

import androidx.lifecycle.LiveData
import com.partSoftware.heliumplus.features.article.data.db.entity.ArticleEntity
import com.partSoftware.heliumplus.features.authenticate.data.network.model.UserInfoResponse
import com.partSoftware.heliumplus.features.profile.data.db.BookmarkAndArticle

interface ProfileLocalDataSource {

    fun getUserInfo(): UserInfoResponse

    suspend fun getAuthorArticles(authorId: Int): List<ArticleEntity>

    suspend fun insertArticles(articles: List<ArticleEntity>)

    fun getBookmarkedArticles(): LiveData<List<BookmarkAndArticle>>
}