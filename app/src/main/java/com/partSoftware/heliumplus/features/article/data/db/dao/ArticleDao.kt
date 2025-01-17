package com.partSoftware.heliumplus.features.article.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.partSoftware.heliumplus.features.article.data.db.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM article")
    suspend fun getArticles(): List<ArticleEntity>

    @Query("SELECT * FROM article WHERE tagId IN(:tagIds)")
    suspend fun getArticlesByTag(tagIds:List<Int>): List<ArticleEntity>

    @Query("DELETE FROM article")
    suspend fun deleteArticles()

    @Query("SELECT * FROM article WHERE id=:id")
    suspend fun getArticle(id: Int): ArticleEntity
}