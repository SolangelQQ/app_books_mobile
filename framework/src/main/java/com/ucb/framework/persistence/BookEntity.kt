package com.ucb.framework.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val authors: String,
    val publishYear: Int?,
    val coverUrl: String?,
    val createdAt: Long = System.currentTimeMillis()
)