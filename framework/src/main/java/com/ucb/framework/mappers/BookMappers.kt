package com.ucb.framework.mappers

import com.ucb.domain.Book
import com.ucb.framework.dto.BookDto
import com.ucb.framework.persistence.BookEntity

fun BookDto.toEntity(): BookEntity {
    return BookEntity(
        id = this.id,
        title = this.title,
        authors = this.authors?.joinToString(";") ?: "",
        publishYear = this.publishYear,
        coverUrl = this.coverId?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
    )
}

fun Book.toEntity(): BookEntity {
    return BookEntity(
        id = this.id,
        title = this.title,
        authors = this.authors?.joinToString(";") ?: "",
        publishYear = this.publishYear,
        coverUrl = this.coverUrl
    )
}

fun BookEntity.toDomain(): Book {
    return Book(
        id = this.id,
        title = this.title,
        authors = this.authors.split(";").filter { it.isNotEmpty() },
        publishYear = this.publishYear,
        coverUrl = this.coverUrl
    )
}