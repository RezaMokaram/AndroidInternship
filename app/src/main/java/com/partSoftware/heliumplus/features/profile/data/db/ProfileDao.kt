package com.partSoftware.heliumplus.features.profile.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.partSoftware.heliumplus.features.article.data.db.entity.ArticleEntity

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM article WHERE authorId = :receivedAuthorId")
    suspend fun getAuthorArticles(receivedAuthorId: Int): List<ArticleEntity>
}