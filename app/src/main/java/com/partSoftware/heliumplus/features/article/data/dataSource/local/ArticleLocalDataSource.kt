package com.partSoftware.heliumplus.features.article.data.dataSource.local

import androidx.lifecycle.LiveData
import com.partSoftware.heliumplus.features.article.data.db.entity.ArticleEntity
import com.partSoftware.heliumplus.features.article.data.db.entity.TagEntity
import com.partSoftware.heliumplus.features.profile.data.db.BookmarkEntity

interface ArticleLocalDataSource {

    fun getTags(): LiveData<List<TagEntity>?>?

    suspend fun getSelectedTagsIds(): List<Int>

    fun getCheckedTagsIds(): List<Int>

    fun getCheckedTagsIdsLiveData(): LiveData<List<Int>?>?

    suspend fun insertTags(tags: List<TagEntity>)

    suspend fun deleteTags()

    suspend fun updateTags(tags: List<TagEntity>)

    suspend fun getArticles(): List<ArticleEntity>

    suspend fun getArticlesByTag(tagIds: List<Int>): List<ArticleEntity>

    suspend fun insertArticles(articles: List<ArticleEntity>)

    suspend fun deleteArticles()

    suspend fun getArticle(id: Int): ArticleEntity

    suspend fun insertBookmarkEntity(bookmarkEntity: BookmarkEntity)

    fun isBookmarked(articleId: Int?): Boolean

    suspend fun deleteBookmarkEntity(bookmarkEntity: BookmarkEntity)

    suspend fun deleteBookmarks()
}