package com.ucb.usecases.books

import com.ucb.data.BookRepository
import com.ucb.domain.Book
import javax.inject.Inject

class GetBookDetails @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(bookId: String): Book {
        // Primero busca en local, si no está, busca en remoto
        return if (repository.isBookSaved(bookId)) {
            repository.getSavedBookDetails(bookId)
        } else {
            repository.getRemoteBookDetails(bookId)
        }
    }
}