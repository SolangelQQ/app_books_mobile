package com.ucb.usecases.books

import com.ucb.data.BookRepository
import com.ucb.domain.Book
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedBooks @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(): Flow<List<Book>> {
        return repository.getSavedBooks()
    }
}