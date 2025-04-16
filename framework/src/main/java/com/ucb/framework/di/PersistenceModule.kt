package com.ucb.framework.di

import android.content.Context
import com.ucb.framework.persistence.AppRoomDatabase
import com.ucb.framework.persistence.BookDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppRoomDatabase {
        return AppRoomDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideBookDao(database: AppRoomDatabase): BookDao {
        return database.bookDao()
    }
}