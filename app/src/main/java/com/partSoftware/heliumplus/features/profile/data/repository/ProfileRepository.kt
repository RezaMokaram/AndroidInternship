package com.partSoftware.heliumplus.features.profile.data.repository

import androidx.lifecycle.LiveData
import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.features.article.ui.model.ArticleView
import com.partSoftware.heliumplus.features.authenticate.ui.model.UserInfoView

interface ProfileRepository {

    fun getUserInfo(): UserInfoView

    suspend fun getAuthorArticles(authorId: Int): ResultWrapper<List<ArticleView>?>

    fun getBookmarkedArticles(): LiveData<List<ArticleView>>
}