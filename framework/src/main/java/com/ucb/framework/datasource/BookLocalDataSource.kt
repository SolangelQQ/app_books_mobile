package com.ucb.framework.datasource


import com.ucb.domain.Book
import com.ucb.framework.persistence.BookDao
import com.ucb.framework.persistence.BookEntity
import com.ucb.framework.mappers.toDomain
import com.ucb.framework.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookLocalDataSource @Inject constructor(
    private val bookDao: BookDao
) {
    fun getSavedBooks(): Flow<List<Book>> {
        return bookDao.getAll().map { books ->
            books.map { it.toDomain() }
        }
    }

    suspend fun saveBook(book: Book) {
        bookDao.insert(book.toEntity())
    }

    suspend fun deleteBook(bookId: String) {
        bookDao.delete(bookId)
    }

    suspend fun isBookSaved(bookId: String): Boolean {
        return bookDao.isBookSaved(bookId)
    }
    suspend fun getBookDetails(bookId: String): Book {
        return bookDao.getBookById(bookId).toDomain()
    }

}