package com.ucb.framework.di

import com.ucb.data.BookRepository
import com.ucb.framework.data.BookRepositoryImpl
import com.ucb.framework.datasource.BookLocalDataSource
import com.ucb.framework.datasource.BookRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookModule {

    @Provides
    @Singleton
    fun provideBookRepository(
        remoteDataSource: BookRemoteDataSource,
        localDataSource: BookLocalDataSource
    ): BookRepository {
        return BookRepositoryImpl(remoteDataSource, localDataSource)
    }
}