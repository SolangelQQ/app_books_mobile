package com.ucb.domain

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val publishYear: Int?,
    val coverUrl: String?,
    val isSaved: Boolean = false
) {
    fun getAuthorsFormatted(): String {
        return authors?.joinToString(", ") ?: "Autor desconocido"
    }
}