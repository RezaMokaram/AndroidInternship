package com.partSoftware.heliumplus.features.profile.data.datasource.local

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.partSoftware.heliumplus.core.data.Constants
import com.partSoftware.heliumplus.features.article.data.db.entity.ArticleEntity
import com.partSoftware.heliumplus.features.authenticate.data.network.model.UserInfoResponse
import com.partSoftware.heliumplus.features.profile.data.db.BookmarkAndArticle
import com.partSoftware.heliumplus.features.profile.data.db.BookmarkDao
import com.partSoftware.heliumplus.features.profile.data.db.ProfileDao
import javax.inject.Inject

class ProfileLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val profileDao: ProfileDao,
    private val bookmarkDao: BookmarkDao
) : ProfileLocalDataSource {

    override fun getUserInfo(): UserInfoResponse {
        val userInfoJson = sharedPreferences.getString(Constants.USER_INFO, "")
        return Gson().fromJson(userInfoJson, UserInfoResponse::class.java)
    }

    override suspend  fun getAuthorArticles(authorId: Int): List<ArticleEntity> =
        profileDao.getAuthorArticles(authorId)

    override suspend  fun insertArticles(articles: List<ArticleEntity>) =
        profileDao.insertArticles(articles)

    override fun getBookmarkedArticles(): LiveData<List<BookmarkAndArticle>> =
        bookmarkDao.getBookmarkedArticles()
}