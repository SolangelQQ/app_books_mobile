package com.ucb.usecases.books

import com.ucb.data.BookRepository
import javax.inject.Inject


class IsBookSaved @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(bookId: String): Boolean {
        return repository.isBookSaved(bookId)
    }
}