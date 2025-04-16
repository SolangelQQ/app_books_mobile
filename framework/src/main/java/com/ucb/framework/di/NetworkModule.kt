package com.ucb.framework.di

import android.content.Context
import com.ucb.framework.datasource.BookRemoteDataSource
import com.ucb.framework.service.OpenLibraryApiService
import com.ucb.framework.service.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        @ApplicationContext context: Context // Añade esta anotación
    ): RetrofitBuilder {
        return RetrofitBuilder(context)
    }

    @Provides
    @Singleton
    fun provideOpenLibraryApiService(retrofitBuilder: RetrofitBuilder): OpenLibraryApiService {
        return retrofitBuilder.openLibraryApiService
    }

    @Provides
    @Singleton
    fun provideBookRemoteDataSource(apiService: OpenLibraryApiService): BookRemoteDataSource {
        return BookRemoteDataSource(apiService)
    }
}