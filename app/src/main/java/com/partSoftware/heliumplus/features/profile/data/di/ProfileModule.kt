package com.partSoftware.heliumplus.features.profile.data.di

import com.partSoftware.heliumplus.core.dp.AppDataBase
import com.partSoftware.heliumplus.features.profile.data.datasource.local.ProfileLocalDataSource
import com.partSoftware.heliumplus.features.profile.data.datasource.local.ProfileLocalDataSourceImpl
import com.partSoftware.heliumplus.features.profile.data.datasource.remote.ProfileRemoteDataSource
import com.partSoftware.heliumplus.features.profile.data.datasource.remote.ProfileRemoteDataSourceImpl
import com.partSoftware.heliumplus.features.profile.data.network.api.ProfileApi
import com.partSoftware.heliumplus.features.profile.data.repository.ProfileRepository
import com.partSoftware.heliumplus.features.profile.data.repository.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    @Binds
    abstract fun provideProfileRemoteDataSource(
        profileRemoteDataSourceImpl: ProfileRemoteDataSourceImpl
    ): ProfileRemoteDataSource

    @Binds
    abstract fun provideProfileLocalDataSource(
        profileLocalDataSourceImpl: ProfileLocalDataSourceImpl
    ): ProfileLocalDataSource

    @Binds
    abstract fun provideProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository


    companion object {
        @Provides
        fun provideProfileApi(retrofit: Retrofit): ProfileApi =
            retrofit.create(ProfileApi::class.java)

        @Provides
        fun provideProfileDao(dataBase: AppDataBase) = dataBase.profileDao()

        @Provides
        fun provideBookmarkDao(dataBase: AppDataBase) = dataBase.bookmark()
    }
}