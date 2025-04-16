package com.ucb.usecases.books

import com.ucb.data.BookRepository
import javax.inject.Inject

class DeleteBook @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(bookId: String) {
        repository.deleteBook(bookId)
    }
}