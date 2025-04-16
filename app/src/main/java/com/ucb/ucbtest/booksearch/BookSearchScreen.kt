package com.ucb.ucbtest.booksearch

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ucb.domain.Book
import com.ucb.ucbtest.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchScreen(
    navController: NavController,
    viewModel: BookSearchViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    val searchState by viewModel.searchState.collectAsState()
    val savedBooks by viewModel.savedBooksState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Buscador de Libros") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { viewModel.search(query) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (val state = searchState) {
                is BookSearchUiState.Empty -> {
                    Text("Ingresa un término de búsqueda", modifier = Modifier.padding(16.dp))
                }
                is BookSearchUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is BookSearchUiState.Success -> {
                    if (state.books.isEmpty()) {
                        Text("No se encontraron resultados", modifier = Modifier.padding(16.dp))
                    } else {
                        BookList(
                            books = state.books,
                            onBookClick = { book ->
                                println("Original book ID: ${book.id}") // Ver en Logcat
                                val cleanBookId = book.id.removePrefix("/").removePrefix("works/")
                                println("Cleaned book ID: $cleanBookId") // Ver en Logcat
                                navController.navigate(Screen.BookDetailScreen.createRoute(cleanBookId))
                                Log.d("NAVIGATION", "Attempting to navigate with book ID: ${book.id}")
                            },
                            onToggleSave = { book ->
                                viewModel.toggleSaveBook(book)
                            }
                        )
                    }
                }
                is BookSearchUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            label = { Text("Buscar libros") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch() }
            )
        )
        Spacer(Modifier.width(8.dp))
        Button(onClick = onSearch) {
            Text("Buscar")
        }
    }
}

@Composable
fun BookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    onToggleSave: (Book) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(books) { book ->
            BookItem(
                book = book,
                onClick = { onBookClick(book) },
                onToggleSave = { onToggleSave(book) }
            )
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    onClick: () -> Unit,
    onToggleSave: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = book.coverUrl ?: "",
                contentDescription = "Portada de ${book.title}",
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.getAuthorsFormatted(),
                    style = MaterialTheme.typography.bodySmall
                )
                book.publishYear?.let {
                    Text(
                        text = "Año: $it",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            IconButton(onClick = onToggleSave) {
                Icon(
                    imageVector = if (book.isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (book.isSaved) "Eliminar de guardados" else "Guardar libro",
                    tint = if (book.isSaved) Color.Red else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}