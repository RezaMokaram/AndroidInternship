package com.partSoftware.heliumplus.features.article.data.dataSource.local

import androidx.lifecycle.LiveData
import com.partSoftware.heliumplus.features.article.data.db.dao.ArticleDao
import com.partSoftware.heliumplus.features.article.data.db.dao.TagDao
import com.partSoftware.heliumplus.features.article.data.db.entity.ArticleEntity
import com.partSoftware.heliumplus.features.article.data.db.entity.TagEntity
import com.partSoftware.heliumplus.features.profile.data.db.BookmarkDao
import com.partSoftware.heliumplus.features.profile.data.db.BookmarkEntity
import javax.inject.Inject

class ArticleLocalDataSourceImpl @Inject
constructor(
    private val articleDao: ArticleDao,
    private val tagDao: TagDao,
    private val bookmarkDao: BookmarkDao
) :
    ArticleLocalDataSource {

    override fun getTags(): LiveData<List<TagEntity>?>? =
        tagDao.getTags()

    override suspend fun getSelectedTagsIds(): List<Int> = tagDao.getSelectedTagsIds()

    override fun getCheckedTagsIds(): List<Int> = tagDao.getCheckedTagsIds()

    override fun getCheckedTagsIdsLiveData(): LiveData<List<Int>?>? =
        tagDao.getCheckedTagsIdsLiveData()

    override suspend fun insertTags(tags: List<TagEntity>) = tagDao.insertTags(tags)

    override suspend fun deleteTags() = tagDao.deleteTags()

    override suspend fun updateTags(tags: List<TagEntity>) = tagDao.updateTags(tags)

    override suspend fun getArticles(): List<ArticleEntity> = articleDao.getArticles()

    override suspend fun insertArticles(articles: List<ArticleEntity>) =
        articleDao.insertArticles(articles)

    override suspend fun deleteArticles() = articleDao.deleteArticles()

    override suspend fun getArticle(id: Int): ArticleEntity = articleDao.getArticle(id)

    override suspend fun insertBookmarkEntity(bookmarkEntity: BookmarkEntity) =
        bookmarkDao.insertBookmarkEntity(bookmarkEntity)

    override fun isBookmarked(articleId: Int?): Boolean =
        bookmarkDao.isBookmarked(articleId)

    override suspend fun deleteBookmarkEntity(bookmarkEntity: BookmarkEntity) =
        bookmarkDao.deleteBookmarkEntity(bookmarkEntity)

    override suspend fun deleteBookmarks() = bookmarkDao.deleteBookmarks()

    override suspend fun getArticlesByTag(tagIds: List<Int>): List<ArticleEntity> =
        articleDao.getArticlesByTag(tagIds)
}
