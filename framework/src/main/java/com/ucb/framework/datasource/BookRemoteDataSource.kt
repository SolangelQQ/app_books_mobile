package com.ucb.framework.datasource

import com.ucb.domain.Book
import com.ucb.framework.service.OpenLibraryApiService
import javax.inject.Inject

class BookRemoteDataSource @Inject constructor(
    private val apiService: OpenLibraryApiService
) {
    suspend fun searchBooks(query: String): List<Book> {
        val response = apiService.searchBooks(query)
        return response.books.map { it.toDomain() }
    }
    suspend fun getBookDetails(bookId: String): Book {
        val cleanId = bookId.removePrefix("/works/").removeSuffix(".json")
        val response = apiService.getBookDetails(cleanId)
        return response.toDomain().copy(id = bookId)
    }

}