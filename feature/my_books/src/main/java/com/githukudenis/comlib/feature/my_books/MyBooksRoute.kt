
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
package com.githukudenis.comlib.feature.my_books

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.CLibCircularProgressBar
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.core.model.book.Book
import kotlinx.coroutines.launch

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
        //        onRetry = viewModel::onRetry,
        onNavigateUp = onNavigateUp,
        onOpenBookDetails = onNavigateToBookDetails,
        onNavigateToAddBook = onNavigateToAddBook
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyBooksContent(
    state: MyBooksUiState,
    onNavigateUp: () -> Unit,
    onOpenBookDetails: (String) -> Unit,
    onNavigateToAddBook: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val titles =
        listOf(
            stringResource(R.string.owned),
            stringResource(R.string.read),
            stringResource(R.string.favourites)
        )
    val pagerState = rememberPagerState(pageCount = { titles.size })

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.my_books_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = pagerState.currentPage == 0,
                enter =
                    slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight } // Slide in from bottom
                    ),
                exit =
                    slideOutVertically(
                        targetOffsetY = { fullHeight -> fullHeight } // Slide out downwards
                    )
            ) {
                FloatingActionButton(onClick = onNavigateToAddBook) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        contentDescription = stringResource(id = R.string.add_book)
                    )
                }
            }
        }
    ) { innerPadding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CLibCircularProgressBar()
            }
            return@Scaffold
        }

        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            BooksTabRow(
                selectedTabIndex = pagerState.currentPage,
                titles = titles,
                onSelectTabIndex = { scope.launch { pagerState.animateScrollToPage(it) } }
            )

            if (state.error?.isNotEmpty() == true) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(horizontal = LocalDimens.current.sixteen),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.error,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                return@Column
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CLibCircularProgressBar()
                }
                return@Column
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize().padding(horizontal = LocalDimens.current.sixteen)
            ) { page ->
                when (page) {
                    0 -> { // Owned books
                        BookListScrollableContent(
                            books = state.owned,
                            emptyMessage = stringResource(id = R.string.empty_owned),
                            onOpenBookDetails = onOpenBookDetails
                        )
                    }
                    1 -> { // Read books
                        BookListScrollableContent(
                            books = state.read,
                            emptyMessage = stringResource(id = R.string.empty_read),
                            onOpenBookDetails = onOpenBookDetails
                        )
                    }
                    2 -> { // Favourite books
                        BookListScrollableContent(
                            books = state.favourite,
                            emptyMessage = stringResource(id = R.string.empty_favourites),
                            onOpenBookDetails = onOpenBookDetails
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookListScrollableContent(
    books: List<Book>, // List of books to display
    emptyMessage: String, // Empty state message
    onOpenBookDetails: (String) -> Unit // Function to handle book click
) {
    if (books.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = emptyMessage,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(books, key = { it.id }) { book ->
                BookComponent(book = book, onOpenBookDetails = onOpenBookDetails)
            }
        }
    }
}

@Composable
fun BookComponent(book: Book, onOpenBookDetails: (String) -> Unit) {
    Row(
        modifier =
            Modifier.fillMaxWidth()
                .clickable { onOpenBookDetails(book.id) }
                .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            AsyncImage(
                modifier = Modifier.size(50.dp).clip(CircleShape),
                model = book.image,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = book.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = buildString { book.authors.map { author -> append(author) } },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        IconButton(onClick = { onOpenBookDetails(book.id) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = "Open book details"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksTabRow(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    titles: List<String>,
    onSelectTabIndex: (Int) -> Unit
) {
    SecondaryTabRow(modifier = modifier.fillMaxWidth(), selectedTabIndex = selectedTabIndex) {
        titles.forEachIndexed { index, title ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onSelectTabIndex(index) },
                text = { Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis) }
            )
        }
    }
}
