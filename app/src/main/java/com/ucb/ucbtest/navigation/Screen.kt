package com.ucb.ucbtest.navigation

sealed class Screen(val route: String) {
    object BookSearchScreen : Screen("bookSearch")
    object BookDetailScreen : Screen("bookDetail/{bookId}") {
        fun createRoute(bookId: String) = "bookDetail/${bookId.removePrefix("/").removePrefix("works/")}"
    }
}