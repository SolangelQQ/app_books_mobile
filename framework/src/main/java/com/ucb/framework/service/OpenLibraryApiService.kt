package com.ucb.framework.service

import com.ucb.framework.dto.BookDetailResponse
import com.ucb.framework.dto.BookResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenLibraryApiService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): BookResponse

    @GET("works/{id}.json")
    suspend fun getBookDetails(
        @Path("id") bookId: String
    ): BookDetailResponse
}