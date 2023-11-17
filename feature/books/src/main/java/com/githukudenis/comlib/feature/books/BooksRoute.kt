package com.githukudenis.comlib.feature.books

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.comlib.core.designsystem.ui.components.loadingBrush

@Composable
fun BooksRoute(
    viewModel: BooksViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    BooksScreen(
        state = state, onChangeGenre = viewModel::onChangeGenre
    )
}

@Composable
fun BooksScreen(
    state: BooksUiState, onChangeGenre: (GenreUiModel) -> Unit
) {
    when (state) {
        is BooksUiState.Loading -> LoadingScreen()
        is BooksUiState.Success -> {
            LoadedScreen(
                genreListUiState = state.genreListUiState,
                bookListUiState = state.bookListUiState,
                onChangeGenre = onChangeGenre
            )
        }

        is BooksUiState.Error -> ErrorScreen()
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

@Composable
private fun LoadedScreen(
    genreListUiState: GenreListUiState,
    bookListUiState: BookListUiState,
    onChangeGenre: (GenreUiModel) -> Unit
) {
    LazyColumn {
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
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                }

                is GenreListUiState.Success -> {
                    LazyRow {
                        items(items = genreListUiState.genres, key = { it.id }) { genre ->
                            Box(modifier = Modifier
                                .clip(CircleShape)
                                .background(
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
                                )
                                .clickable { onChangeGenre(genre) }) {
                                Text(
                                    text = genre.name, modifier = Modifier.padding(
                                            horizontal = 16.dp, vertical = 8.dp
                                        )
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

    }
}

@Composable
private fun ErrorScreen() {

}