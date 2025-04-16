package com.ucb.framework.data

import com.ucb.data.BookRepository
import com.ucb.data.NetworkResult
import com.ucb.domain.Book
import com.ucb.framework.datasource.BookLocalDataSource
import com.ucb.framework.datasource.BookRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val remoteDataSource: BookRemoteDataSource,
    private val localDataSource: BookLocalDataSource
) : BookRepository {

    override suspend fun searchBooks(query: String): NetworkResult<List<Book>> {
        return try {
            val remoteBooks = remoteDataSource.searchBooks(query)
            NetworkResult.Success(remoteBooks)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Error desconocido al buscar libros")
        }
    }

    override suspend fun saveBook(book: Book) {
        localDataSource.saveBook(book)
    }

    override suspend fun deleteBook(bookId: String) {
        localDataSource.deleteBook(bookId)
    }

    override fun getSavedBooks(): Flow<List<Book>> {
        return localDataSource.getSavedBooks()
    }

    override suspend fun isBookSaved(bookId: String): Boolean {
        return localDataSource.isBookSaved(bookId)
    }

    override suspend fun getRemoteBookDetails(bookId: String): Book {
        return remoteDataSource.getBookDetails(bookId)
    }

    override suspend fun getSavedBookDetails(bookId: String): Book {
        return localDataSource.getBookDetails(bookId)
    }
}