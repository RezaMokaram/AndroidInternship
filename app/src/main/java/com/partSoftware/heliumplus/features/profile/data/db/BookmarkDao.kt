package com.partSoftware.heliumplus.features.profile.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkEntity(bookmarkEntity: BookmarkEntity)

    @Delete
    suspend fun deleteBookmarkEntity(bookmarkEntity: BookmarkEntity)

    @Query("DELETE FROM bookmark")
    suspend fun deleteBookmarks()

    @Query("SELECT EXISTS(SELECT 1 FROM bookmark WHERE articleId=:articleId)")
    fun isBookmarked(articleId: Int?): Boolean

    @Transaction
    @Query("SELECT * FROM bookmark")
    fun getBookmarkedArticles(): LiveData<List<BookmarkAndArticle>>

}