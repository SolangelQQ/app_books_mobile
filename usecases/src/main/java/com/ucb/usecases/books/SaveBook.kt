package com.ucb.usecases.books


import com.ucb.data.BookRepository
import com.ucb.domain.Book
import javax.inject.Inject

class SaveBook @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(book: Book) {
        repository.saveBook(book)
    }
}