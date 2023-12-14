package com.partSoftware.heliumplus.features.search.data.dataSource.local

import com.partSoftware.heliumplus.features.article.data.db.entity.ArticleEntity

interface SearchLocalDataSource {
    suspend fun insertArticles(articles: List<ArticleEntity>)
}