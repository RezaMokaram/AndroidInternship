package com.partSoftware.heliumplus.features.article.data.repository

import androidx.lifecycle.LiveData
import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.features.article.ui.model.AddArticleResponseView
import com.partSoftware.heliumplus.features.article.ui.model.AddArticleRequestView
import com.partSoftware.heliumplus.features.article.ui.model.ArticleView
import com.partSoftware.heliumplus.features.article.ui.model.TagView

interface ArticleRepository {

    suspend fun fetchTags(): ResultWrapper<List<TagView>?>?

    fun getTags(): LiveData<List<TagView>?>?

    fun getSelectedTags(): LiveData<List<TagView>?>?

    fun getCheckedTagsIds(): List<Int>

    fun getCheckedTagsIdsLiveData(): LiveData<List<Int>?>?

    suspend fun updateTags(tags: List<TagView>)

    suspend fun getArticles(): ResultWrapper<List<ArticleView>?>

    suspend fun getArticlesByTag(checkedTagsIds:List<Int>): ResultWrapper<List<ArticleView>?>

    suspend fun getArticle(id: Int): ArticleView

    suspend fun addArticle(addArticleRequestView: AddArticleRequestView): ResultWrapper<AddArticleResponseView?>

    suspend fun insertBookmarkEntity(articleId: Int?)

    fun isBookmarked(articleId: Int?): Boolean

    suspend fun deleteBookmarkEntity(articleId: Int)
}