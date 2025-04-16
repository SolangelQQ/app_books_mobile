package com.ucb.framework.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.ucb.domain.Book

@JsonClass(generateAdapter = true)
data class BookResponse(
    @Json(name = "numFound") val totalResults: Int,
    @Json(name = "start") val start: Int,
    @Json(name = "numFoundExact") val exactMatch: Boolean,
    @Json(name = "docs") val books: List<BookDto>
)

@JsonClass(generateAdapter = true)
data class BookDto(
    @Json(name = "key") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "author_name") val authors: List<String>?,
    @Json(name = "first_publish_year") val publishYear: Int?,
    @Json(name = "cover_i") val coverId: Int?,
    @Json(name = "edition_count") val editionCount: Int?,
    @Json(name = "language") val languages: List<String>?,
    @Json(name = "subject") val subjects: List<String>?
) {
    fun toDomain(): Book {
        val coverUrl = coverId?.let {
            "https://covers.openlibrary.org/b/id/$it-M.jpg"
        }
        return Book(
            id = id,
            title = title,
            authors = authors,
            publishYear = publishYear,
            coverUrl = coverUrl
        )
    }
}