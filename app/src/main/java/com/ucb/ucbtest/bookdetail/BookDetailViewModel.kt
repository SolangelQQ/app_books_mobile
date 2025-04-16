package com.ucb.ucbtest.bookdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.domain.Book
import com.ucb.usecases.books.DeleteBook
import com.ucb.usecases.books.GetBookDetails
import com.ucb.usecases.books.IsBookSaved
import com.ucb.usecases.books.SaveBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val getBookDetails: GetBookDetails,
    private val saveBook: SaveBook,
    private val deleteBook: DeleteBook,
    private val isBookSaved: IsBookSaved,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val bookId: String = checkNotNull(savedStateHandle["bookId"])

    private val _bookState = MutableStateFlow<BookDetailUiState>(BookDetailUiState.Loading)
    val bookState: StateFlow<BookDetailUiState> = _bookState

    private val _isSaved = MutableStateFlow(false)
    val isSaved = _isSaved.asStateFlow()

    init {
        println("Inicializando BookDetailViewModel con ID: $bookId")
        if (bookId.isEmpty()) {
            println("ERROR: bookId está vacío")
        }
        loadBookDetails(bookId)
        checkIfSaved(bookId)
    }

    fun loadBookDetails(bookId: String) {
        viewModelScope.launch {
            _bookState.value = BookDetailUiState.Loading
            try {
                val book = getBookDetails(bookId)
                _bookState.value = BookDetailUiState.Success(book)
            } catch (e: Exception) {
                _bookState.value = BookDetailUiState.Error(e.message ?: "Error al cargar detalles")
            }
        }
    }

    fun checkIfSaved(bookId: String) {
        viewModelScope.launch {
            _isSaved.value = isBookSaved(bookId)
        }
    }

    fun toggleSave() {
        viewModelScope.launch {
            if (isSaved.value) {
                deleteBook(bookId)
            } else {
                val currentState = _bookState.value
                if (currentState is BookDetailUiState.Success) {
                    saveBook(currentState.book)
                }
            }
            _isSaved.value = !isSaved.value
        }
    }
}

sealed class BookDetailUiState {
    object Loading : BookDetailUiState()
    data class Success(val book: Book) : BookDetailUiState()
    data class Error(val message: String) : BookDetailUiState()
}