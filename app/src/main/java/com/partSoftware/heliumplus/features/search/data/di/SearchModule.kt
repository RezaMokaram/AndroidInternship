package com.partSoftware.heliumplus.features.search.data.di

import com.partSoftware.heliumplus.core.dp.AppDataBase
import com.partSoftware.heliumplus.features.search.data.dataSource.local.SearchLocalDataSource
import com.partSoftware.heliumplus.features.search.data.dataSource.local.SearchLocalDataSourceImpl
import com.partSoftware.heliumplus.features.search.data.dataSource.remote.SearchRemoteDataSource
import com.partSoftware.heliumplus.features.search.data.dataSource.remote.SearchRemoteDataSourceImpl
import com.partSoftware.heliumplus.features.search.data.network.api.SearchApi
import com.partSoftware.heliumplus.features.search.data.repository.SearchRepository
import com.partSoftware.heliumplus.features.search.data.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {
    @Binds
    abstract fun provideSearchRemoteDataSource(
        searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl
    ): SearchRemoteDataSource

    @Binds
    abstract fun provideSearchLocalDataSource(
        searchLocalDataSourceImpl: SearchLocalDataSourceImpl
    ): SearchLocalDataSource

    @Binds
    abstract fun provideSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    companion object {
        @Provides
        fun provideSearchApi(retrofit: Retrofit): SearchApi =
            retrofit.create(SearchApi::class.java)

        @Provides
        fun provideSearchDao(dataBase: AppDataBase) = dataBase.searchDao()
    }
}