package com.partSoftware.heliumplus.features.article.data.di

import com.partSoftware.heliumplus.core.dp.AppDataBase
import com.partSoftware.heliumplus.features.article.data.dataSource.local.ArticleLocalDataSource
import com.partSoftware.heliumplus.features.article.data.dataSource.local.ArticleLocalDataSourceImpl
import com.partSoftware.heliumplus.features.article.data.dataSource.remote.ArticleRemoteDataSource
import com.partSoftware.heliumplus.features.article.data.dataSource.remote.ArticleRemoteDataSourceImpl
import com.partSoftware.heliumplus.features.article.data.network.api.ArticleApi
import com.partSoftware.heliumplus.features.article.data.repository.ArticleRepository
import com.partSoftware.heliumplus.features.article.data.repository.ArticleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleModule {

    @Binds
    abstract fun provideArticleRemoteDataSource(
        articleRemoteDataSourceImpl: ArticleRemoteDataSourceImpl
    ): ArticleRemoteDataSource

    @Binds
    abstract fun provideArticleLocalDataSource(
        articleLocalDataSourceImpl: ArticleLocalDataSourceImpl
    ): ArticleLocalDataSource

    @Binds
    abstract fun provideArticleRepository(
        articleRepositoryImpl: ArticleRepositoryImpl
    ): ArticleRepository


    companion object {
        @Provides
        fun provideArticleApi(retrofit: Retrofit): ArticleApi =
            retrofit.create(ArticleApi::class.java)

        @Provides
        fun provideArticleDao(dataBase: AppDataBase) = dataBase.articleDao()

        @Provides
        fun provideTagDao(dataBase: AppDataBase) = dataBase.tagDao()
    }
}