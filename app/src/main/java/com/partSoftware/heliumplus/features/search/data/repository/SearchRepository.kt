package com.partSoftware.heliumplus.features.search.data.repository

import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.features.article.ui.model.ArticleView
import com.partSoftware.heliumplus.features.search.ui.model.SearchRequestView
import com.partSoftware.heliumplus.features.search.ui.model.TagViewSearch
import com.partSoftware.heliumplus.features.search.ui.model.UsersViewSearch

interface SearchRepository {

    suspend fun getTags(searchRequestView: SearchRequestView): ResultWrapper<List<TagViewSearch>?>

    suspend fun getUsers(searchRequestView: SearchRequestView): ResultWrapper<List<UsersViewSearch>?>

    suspend fun getArticles(searchRequestView: SearchRequestView): ResultWrapper<List<ArticleView>?>
}