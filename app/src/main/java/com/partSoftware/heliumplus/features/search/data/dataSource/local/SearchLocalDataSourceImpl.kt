package com.partSoftware.heliumplus.features.search.data.dataSource.local

import com.partSoftware.heliumplus.features.article.data.db.entity.ArticleEntity
import com.partSoftware.heliumplus.features.search.data.db.dao.SearchDao
import javax.inject.Inject

class SearchLocalDataSourceImpl @Inject constructor(private val searchDao: SearchDao) :
    SearchLocalDataSource {

    override suspend fun insertArticles(articles: List<ArticleEntity>) =
        searchDao.insertArticles(articles)
}