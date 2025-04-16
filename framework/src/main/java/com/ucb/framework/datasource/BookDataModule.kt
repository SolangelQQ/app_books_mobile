//package com.ucb.framework.datasource
//
//import com.ucb.framework.persistence.BookDao
//import com.ucb.framework.service.OpenLibraryApiService
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object BookDataModule {
//
//    @Provides
//    @Singleton
//    fun provideBookRemoteDataSource(
//        openLibraryApiService: OpenLibraryApiService
//    ): BookRemoteDataSource {
//        return BookRemoteDataSource(openLibraryApiService)
//    }
//
//    @Provides
//    @Singleton
//    fun provideBookLocalDataSource(
//        bookDao: BookDao
//    ): BookLocalDataSource {
//        return BookLocalDataSource(bookDao)
//    }
//}