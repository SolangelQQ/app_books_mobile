package com.ucb.framework.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.ucb.domain.Book

// BookDetailResponse.kt
@JsonClass(generateAdapter = true)
data class BookDetailResponse(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String?,
    @Json(name = "authors") val authors: List<Author>?,
    @Json(name = "first_publish_date") val firstPublishDate: String?,
    @Json(name = "covers") val coverIds: List<Int>?,
    @Json(name = "subjects") val subjects: List<String>?,
    @Json(name = "subject_places") val subjectPlaces: List<String>?,
    @Json(name = "subject_people") val subjectPeople: List<String>?,
    @Json(name = "subject_times") val subjectTimes: List<String>?
) {
    fun toDomain(): Book {
        return Book(
            id = "", // Necesitarás obtener el ID de otra manera
            title = this.title,
            authors = this.authors?.map { it.author?.key ?: "" } ?: emptyList(),
            publishYear = this.firstPublishDate?.substringBefore("-")?.toIntOrNull(),
            coverUrl = coverIds?.firstOrNull()?.let {
                "https://covers.openlibrary.org/b/id/$it-M.jpg"
            },
            isSaved = false
        )
    }
}

@JsonClass(generateAdapter = true)
data class Author(
    @Json(name = "author") val author: AuthorKey?
)

@JsonClass(generateAdapter = true)
data class AuthorKey(
    @Json(name = "key") val key: String?
)