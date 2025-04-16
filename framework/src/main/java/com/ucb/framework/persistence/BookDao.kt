package com.ucb.framework.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity)

    @Query("SELECT * FROM books ORDER BY createdAt DESC")
    fun getAll(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getById(bookId: String): BookEntity?

    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun delete(bookId: String)

    @Query("SELECT EXISTS(SELECT * FROM books WHERE id = :bookId)")
    suspend fun isBookSaved(bookId: String): Boolean

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: String): BookEntity

}