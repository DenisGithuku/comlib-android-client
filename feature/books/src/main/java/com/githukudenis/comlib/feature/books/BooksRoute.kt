
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.comlib.core.common.untangle
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.loadingBrush
import com.githukudenis.comlib.core.designsystem.ui.components.pills.SelectablePillComponent
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.feature.books.components.BookComponent
import kotlinx.coroutines.launch

@Composable
fun BooksRoute(viewModel: BooksViewModel = hiltViewModel(), onOpenBook: (String) -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    BooksScreen(state = state, onChangeGenre = viewModel::onChangeGenre, onOpenBook = onOpenBook)
}

@Composable
fun BooksScreen(
    state: BooksUiState,
    onChangeGenre: (String) -> Unit,
    onOpenBook: (String) -> Unit
) {
    when (state) {
        is BooksUiState.Loading -> LoadingScreen()
        is BooksUiState.Success -> {
            LoadedScreen(
                selectedGenres = state.selectedGenres,
                genreListUiState = state.genreListUiState,
                bookListUiState = state.bookListUiState,
                onChangeGenre = onChangeGenre,
                onOpenBook = onOpenBook
            )
        }
        is BooksUiState.Error -> ErrorScreen()
    }
}

@Composable
private fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(LocalDimens.current.extraLarge),
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.medium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.small)
        ) {
            (0..4).forEach { LoadingGenrePill() }
        }
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.Black.copy(0.09f))
        Column(verticalArrangement = Arrangement.spacedBy(LocalDimens.current.small)) {
            (0..8).forEach { LoadingBookCard() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadedScreen(
    selectedGenres: List<GenreUiModel>,
    genreListUiState: GenreListUiState,
    bookListUiState: BookListUiState,
    onChangeGenre: (String) -> Unit,
    onOpenBook: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var bottomSheetIsVisible: Boolean by remember { mutableStateOf(false) }

    if (bottomSheetIsVisible) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            onDismissRequest = { bottomSheetIsVisible = false }
        ) {
            Text(
                text = stringResource(R.string.bottom_sheet_tittle),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(LocalDimens.current.medium)
            )
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                when (genreListUiState) {
                    is GenreListUiState.Error -> Unit
                    GenreListUiState.Loading -> Unit
                    is GenreListUiState.Success -> {
                        items(genreListUiState.genres.dropLast(1), key = { it.id }) { genre ->
                            Row(
                                modifier =
                                    Modifier.fillMaxWidth()
                                        .clickable { onChangeGenre(genre.id) }
                                        .padding(LocalDimens.current.medium),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.medium)
                            ) {
                                Checkbox(
                                    checked = genre.id in selectedGenres.map { it.id },
                                    onCheckedChange = { onChangeGenre(genre.id) }
                                )
                                Text(
                                    text = genre.name.untangle("-"),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(vertical = LocalDimens.current.extraLarge)) {
        item {
            when (genreListUiState) {
                is GenreListUiState.Error -> {
                    Text(text = genreListUiState.message, style = MaterialTheme.typography.titleMedium)
                }
                GenreListUiState.Loading -> {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = LocalDimens.current.medium),
                        horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.small)
                    ) {
                        (0..4).map { LoadingGenrePill() }
                    }
                }
                is GenreListUiState.Success -> {
                    LazyRow(
                        modifier = Modifier.padding(LocalDimens.current.small),
                        horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.medium),
                        contentPadding = PaddingValues(LocalDimens.current.extraLarge)
                    ) {
                        val updatedGenres = genreListUiState.genres.take(4).toMutableList()
                        updatedGenres.add(
                            GenreUiModel(
                                context.getString(R.string.default_more_genres_name),
                                context.getString(R.string.default_more_genres_id)
                            )
                        )
                        items(items = updatedGenres, key = { it.id }) { genre ->
                            SelectablePillComponent(
                                value = genre.name.untangle("-"),
                                isSelected = genre.id in selectedGenres.map { it.id },
                                hasIcon = false,
                                id = genre.id,
                                onToggleSelection = {
                                    if (it == context.getString(R.string.default_more_genres_id)) {
                                        scope.launch { bottomSheetIsVisible = !bottomSheetIsVisible }
                                    }
                                    onChangeGenre(it)
                                }
                            )
                        }
                    }
                }
            }
        }
        item { Divider(modifier = Modifier, color = Color.Black.copy(alpha = 0.1f)) }

        when (bookListUiState) {
            is BookListUiState.Error -> {
                item { Text(text = bookListUiState.message, style = MaterialTheme.typography.bodyMedium) }
            }
            BookListUiState.Loading -> {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(LocalDimens.current.small)) {
                        (0..6).map { LoadingBookCard() }
                    }
                }
            }
            is BookListUiState.Success -> {
                items(bookListUiState.books) { bookModel ->
                    BookComponent(
                        bookItemUiModel = bookModel,
                        onOpenBookDetails = { bookId -> onOpenBook(bookId) }
                    )
                }
            }
            BookListUiState.Empty -> {
                item {
                    Text(
                        text = stringResource(R.string.empty_list_label, "books"),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(LocalDimens.current.large)
                    )
                }
            }
        }
    }
}

@Composable private fun ErrorScreen() {}

@Composable
fun LoadingBookCard() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(LocalDimens.current.medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.extraLarge)
    ) {
        Box(modifier = Modifier.size(64.dp).clip(CircleShape).background(loadingBrush()))
        Column {
            Box(
                modifier =
                    Modifier.height(LocalDimens.current.large)
                        .fillMaxWidth(0.8f)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(loadingBrush())
            )
            Spacer(modifier = Modifier.padding(LocalDimens.current.medium))
            Box(
                modifier =
                    Modifier.height(LocalDimens.current.large)
                        .fillMaxWidth(0.4f)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(loadingBrush())
            )
        }
    }
}

@Composable
fun LoadingGenrePill() {
    Box(modifier = Modifier.height(42.dp).width(72.dp).clip(CircleShape).background(loadingBrush()))
}
