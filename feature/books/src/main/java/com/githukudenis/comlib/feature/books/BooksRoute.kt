package com.githukudenis.comlib.feature.books

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.comlib.core.common.untangle
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.loadingBrush
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.feature.books.components.BookComponent

@Composable
fun BooksRoute(
    viewModel: BooksViewModel = hiltViewModel(),
    onOpenBook: (String) -> Unit,
    onNavigateUp: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    BooksScreen(
        state = state,
        onChangeGenre = viewModel::onChangeGenre,
        onOpenBook = onOpenBook,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksScreen(
    state: BooksUiState,
    onChangeGenre: (GenreUiModel) -> Unit,
    onOpenBook: (String) -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = stringResource(R.string.all_books_title),
                style = MaterialTheme.typography.titleMedium
            )
        }, navigationIcon = {
            IconButton(onClick = { onNavigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                )
            }
        })
    }) { innerPadding ->
        when (state) {
            is BooksUiState.Loading -> LoadingScreen()
            is BooksUiState.Success -> {
                LoadedScreen(
                    paddingValues = innerPadding,
                    genreListUiState = state.genreListUiState,
                    bookListUiState = state.bookListUiState,
                    onChangeGenre = onChangeGenre,
                    onOpenBook = onOpenBook
                )
            }

            is BooksUiState.Error -> ErrorScreen()
        }
    }
}

@Composable
private fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (0..4).map {
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(36.dp)
                        .clip(CircleShape)
                        .background(brush = loadingBrush())
                )
            }
        }
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.Black.copy(0.09f))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            (0..6).map {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(brush = loadingBrush())
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Box(
                            modifier = Modifier
                                .height(24.dp)
                                .fillMaxWidth(0.6f)
                                .clip(CircleShape)
                                .background(brush = loadingBrush())
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .height(24.dp)
                                .fillMaxWidth(0.6f)
                                .clip(CircleShape)
                                .background(brush = loadingBrush())
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadedScreen(
    paddingValues: PaddingValues,
    genreListUiState: GenreListUiState,
    bookListUiState: BookListUiState,
    onChangeGenre: (GenreUiModel) -> Unit,
    onOpenBook: (String) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.medium)
    ) {
        item {
            when (genreListUiState) {
                is GenreListUiState.Error -> {
                    Text(
                        text = genreListUiState.message,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                GenreListUiState.Loading -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = LocalDimens.current.large),
                        horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.medium)
                    ) {
                        (0..4).map {
                            Box(
                                modifier = Modifier
                                    .height(48.dp)
                                    .width(72.dp)
                                    .clip(CircleShape)
                                    .background(brush = loadingBrush())
                            )
                        }
                    }
                }

                is GenreListUiState.Success -> {
                    LazyRow(
                        modifier = Modifier.padding(top = LocalDimens.current.small),
                        horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.medium),
                        contentPadding = PaddingValues(LocalDimens.current.extraLarge)
                    ) {
                        items(items = genreListUiState.genres, key = { it.id }) { genre ->
                            Box(modifier = Modifier
                                .clip(CircleShape)
                                .background(
                                    color = MaterialTheme.colorScheme.onBackground.copy(
                                        alpha = 0.1f
                                    )
                                )
                                .clickable { onChangeGenre(genre) }) {
                                Text(
                                    text = genre.name.untangle("-"), modifier = Modifier.padding(
                                        horizontal = LocalDimens.current.extraLarge,
                                        vertical = LocalDimens.current.medium
                                    ), style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
        item {
            Divider(modifier = Modifier, color = Color.Black.copy(alpha = 0.1f))
        }

        when (bookListUiState) {
            is BookListUiState.Error -> {
                item {
                    Text(
                        text = bookListUiState.message, style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            BookListUiState.Loading -> {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(LocalDimens.current.large)) {
                        (0..6).map {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = LocalDimens.current.large,
                                        horizontal = LocalDimens.current.medium
                                    ),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(CircleShape)
                                        .background(brush = loadingBrush())
                                )
                                Spacer(modifier = Modifier.width(LocalDimens.current.extraLarge))
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .height(LocalDimens.current.extraLarge)
                                            .fillMaxWidth(0.6f)
                                            .background(brush = loadingBrush())
                                            .clip(shape = CircleShape)
                                    )
                                    Spacer(modifier = Modifier.height(LocalDimens.current.medium))
                                    Box(
                                        modifier = Modifier
                                            .height(LocalDimens.current.extraLarge)
                                            .fillMaxWidth(0.4f)
                                            .background(brush = loadingBrush())
                                            .clip(shape = CircleShape)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is BookListUiState.Success -> {
                items(bookListUiState.books) { bookModel ->
                    BookComponent(bookItemUiModel = bookModel, onOpenBookDetails = { bookId ->
                        onOpenBook(bookId)
                    })
                }
            }
        }
    }
}

@Composable
private fun ErrorScreen() {

}