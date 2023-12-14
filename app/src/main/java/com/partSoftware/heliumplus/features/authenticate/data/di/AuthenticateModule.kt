package com.partSoftware.heliumplus.features.authenticate.data.di

import com.partSoftware.heliumplus.features.authenticate.data.dataSource.local.AuthenticateLocalDataSource
import com.partSoftware.heliumplus.features.authenticate.data.dataSource.local.AuthenticateLocalDataSourceImpl
import com.partSoftware.heliumplus.features.authenticate.data.dataSource.remote.AuthenticateRemoteDataSource
import com.partSoftware.heliumplus.features.authenticate.data.dataSource.remote.AuthenticateRemoteDataSourceImpl
import com.partSoftware.heliumplus.features.authenticate.data.network.api.AuthenticateApi
import com.partSoftware.heliumplus.features.authenticate.data.repository.AuthenticateRepository
import com.partSoftware.heliumplus.features.authenticate.data.repository.AuthenticateRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticateModule {

    @Binds
    abstract fun provideAuthenticateRemoteDataSource(
        authenticateRemoteDataSourceImpl: AuthenticateRemoteDataSourceImpl
    ): AuthenticateRemoteDataSource

    @Binds
    abstract fun provideAuthenticateLocalDataSource(
        authenticateLocalDataSourceImpl: AuthenticateLocalDataSourceImpl
    ): AuthenticateLocalDataSource

    @Binds
    abstract fun provideAuthenticateRepository(
        authenticateRepositoryImpl: AuthenticateRepositoryImpl
    ): AuthenticateRepository

    companion object {
        @Provides
        fun provideAuthenticateApi(retrofit: Retrofit): AuthenticateApi =
            retrofit.create(AuthenticateApi::class.java)
    }
}