package com.githukudenis.comlib.feature.my_books

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.CLibCircularProgressBar
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.core.model.book.Book

@Composable
fun MyBooksRoute(
    viewModel: MyBooksViewModel = hiltViewModel(),
    onNavigateToBookDetails: (String) -> Unit,
    onNavigateToAddBook: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    MyBooksContent(
        state = state,
        onRetry = viewModel::onRetry,
        onNavigateUp = onNavigateUp,
        onOpenBookDetails = onNavigateToBookDetails,
        onNavigateToAddBook = onNavigateToAddBook
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyBooksContent(
    state: MyBooksUiState,
    onRetry: () -> Unit,
    onNavigateUp: () -> Unit,
    onOpenBookDetails: (String) -> Unit,
    onNavigateToAddBook: () -> Unit
) {

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = stringResource(R.string.my_books_title),
                style = MaterialTheme.typography.titleMedium
            )
        }, navigationIcon = {
            IconButton(onClick = { onNavigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                )
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = onNavigateToAddBook) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                contentDescription = stringResource(id = R.string.add_book)
            )
        }
    }) { innerPadding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CLibCircularProgressBar()
            }
            return@Scaffold
        }

        if (state.error?.isNotEmpty() == true) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.error, style = MaterialTheme.typography.bodyMedium
                )
                return@Scaffold
            }
        }

        if (state.books.isEmpty()) {
            EmptyBooks()
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(LocalDimens.current.medium)
        ) {
            items(state.books, key = { it.id }) { book ->
                BookComponent(book = book, onOpenBookDetails = onOpenBookDetails)
            }
        }
    }
}

@Composable
fun BookComponent(book: Book, onOpenBookDetails: (String) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onOpenBookDetails(book.id)
        }
        .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                model = "https://comlib-api.onrender.com/img/books/${book.image}",
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = buildString {
                        book.authors.map { author ->
                            append(author)
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        IconButton(onClick = { onOpenBookDetails(book.id) }) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open book details",
            )
        }
    }
}

@Composable
fun EmptyBooks() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalDimens.current.extraLarge),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.empty_books_label),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}