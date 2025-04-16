package com.ucb.usecases.books

import com.ucb.data.BookRepository
import com.ucb.data.NetworkResult
import com.ucb.domain.Book
import javax.inject.Inject

class SearchBooks @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(query: String): NetworkResult<List<Book>> {
        return repository.searchBooks(query)
    }
}