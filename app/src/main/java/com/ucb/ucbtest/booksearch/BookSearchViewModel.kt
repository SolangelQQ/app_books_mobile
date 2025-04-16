package com.ucb.ucbtest.booksearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.data.NetworkResult
import com.ucb.domain.Book
import com.ucb.usecases.books.DeleteBook
import com.ucb.usecases.books.GetSavedBooks
import com.ucb.usecases.books.IsBookSaved
import com.ucb.usecases.books.SaveBook
import com.ucb.usecases.books.SearchBooks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val searchBooks: SearchBooks,
    private val saveBook: SaveBook,
    private val deleteBook: DeleteBook,
    private val getSavedBooks: GetSavedBooks,
    private val isBookSaved: IsBookSaved
) : ViewModel() {

    private val _searchState = MutableStateFlow<BookSearchUiState>(BookSearchUiState.Empty)
    val searchState: StateFlow<BookSearchUiState> = _searchState

    private val _savedBooksState = MutableStateFlow<List<Book>>(emptyList())
    val savedBooksState: StateFlow<List<Book>> = _savedBooksState

    private val _uiState = MutableStateFlow<BookUiState>(BookUiState.Idle)
    val uiState: StateFlow<BookUiState> = _uiState

    init {
        loadSavedBooks()
    }

    fun search(query: String) {
        viewModelScope.launch {
            _searchState.value = BookSearchUiState.Loading
            when (val result = searchBooks(query)) {
                is NetworkResult.Success -> {
                    val booksWithSavedStatus = result.data.map { book ->
                        book.copy(isSaved = isBookSaved(book.id))
                    }
                    _searchState.value = BookSearchUiState.Success(booksWithSavedStatus)
                }
                is NetworkResult.Error -> {
                    _searchState.value = BookSearchUiState.Error(result.error)
                }
            }
        }
    }

    fun toggleSaveBook(book: Book) {
        viewModelScope.launch {
            _uiState.value = BookUiState.Loading
            try {
                if (book.isSaved) {
                    deleteBook(book.id)
                } else {
                    saveBook(book)
                }
                // Actualizar estados
                loadSavedBooks()
                updateSearchResults()
                _uiState.value = BookUiState.Success
            } catch (e: Exception) {
                _uiState.value = BookUiState.Error(e.message ?: "Error al guardar/eliminar libro")
            }
        }
    }

    private fun loadSavedBooks() {
        viewModelScope.launch {
            getSavedBooks().collect { books ->
                _savedBooksState.value = books
            }
        }
    }

    private suspend fun updateSearchResults() {
        val currentState = _searchState.value
        if (currentState is BookSearchUiState.Success) {
            val updatedBooks = currentState.books.map { book ->
                book.copy(isSaved = isBookSaved(book.id))
            }
            _searchState.value = BookSearchUiState.Success(updatedBooks)
        }
    }
}

// Estados para la UI
sealed class BookSearchUiState {
    object Empty : BookSearchUiState()
    object Loading : BookSearchUiState()
    data class Success(val books: List<Book>) : BookSearchUiState()
    data class Error(val message: String) : BookSearchUiState()
}

sealed class BookUiState {
    object Idle : BookUiState()
    object Loading : BookUiState()
    object Success : BookUiState()
    data class Error(val message: String) : BookUiState()
}