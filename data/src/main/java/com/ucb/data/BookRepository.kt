package com.ucb.data

import com.ucb.domain.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): NetworkResult<List<Book>>
    suspend fun saveBook(book: Book)
    suspend fun deleteBook(bookId: String)
    fun getSavedBooks(): Flow<List<Book>>
    suspend fun isBookSaved(bookId: String): Boolean
    suspend fun getSavedBookDetails(string: String): Book
    suspend fun getRemoteBookDetails(string: String): Book
}