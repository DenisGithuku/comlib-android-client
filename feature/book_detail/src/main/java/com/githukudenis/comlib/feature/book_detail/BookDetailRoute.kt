package com.githukudenis.comlib.feature.book_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.common.untangle
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibOutlinedButton
import com.githukudenis.comlib.core.designsystem.ui.components.loadingBrush

@Composable
fun BookDetailRoute(
    viewModel: BookDetailViewModel = hiltViewModel(), onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state, onRetry = { viewModel.onRetry() }, onBackPressed = onBackPressed
    )
}

@Composable
fun BookDetailScreen(
    state: BookDetailUiState, onRetry: () -> Unit, onBackPressed: () -> Unit
) {
    when (state) {
        is BookDetailUiState.Loading -> {
            LoadingScreen(onBackPressed = onBackPressed)
        }

        is BookDetailUiState.Success -> {
            LoadedScreen(bookUiModel = state.bookUiModel,
                onBackPressed = onBackPressed,
                onToggleFavourite = {})
        }

        is BookDetailUiState.Error -> {
            ErrorScreen(message = state.message, onRetry = onRetry)
        }
    }
}

@Composable
fun LoadingScreen(onBackPressed: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(brush = loadingBrush())
        ) {
            Box(
                modifier = Modifier
                    .safeDrawingPadding()
                    .padding(horizontal = 16.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
                    .clickable {
                        onBackPressed()
                    },
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.4f)
                    .clip(CircleShape)
                    .background(brush = loadingBrush())
            )
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(brush = loadingBrush())
            )
        }
        Box(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth(0.6f)
                .padding(horizontal = 16.dp)
                .clip(CircleShape)
                .background(brush = loadingBrush())
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            (0..2).map {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(70.dp)
                        .clip(CircleShape)
                        .background(brush = loadingBrush())
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), color = Color.LightGray, thickness = 1.dp
        )
        Box(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth(0.4f)
                .clip(CircleShape)
                .padding(horizontal = 16.dp)
                .background(brush = loadingBrush())
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            (0..5).map {
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                        .clip(CircleShape)
                        .background(brush = loadingBrush())
                )
            }
        }
    }
}

@Composable
fun LoadedScreen(
    bookUiModel: BookUiModel, onBackPressed: () -> Unit, onToggleFavourite: (String) -> Unit, onReserve: (String) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier) {
                AsyncImage(
                    model = "https://comlib-api.onrender.com/img/books/${bookUiModel.imageUrl}",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.FillWidth
                )
                Box(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .padding(horizontal = 16.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
                        .clickable {
                            onBackPressed()
                        },
                ) {
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.background
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = bookUiModel.title, style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = { onToggleFavourite(bookUiModel.id) }) {
                    Icon(
                        imageVector = if (bookUiModel.isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        tint = if (bookUiModel.isFavourite) Color.Yellow.copy(green = 0.7f) else MaterialTheme.colorScheme.onBackground,
                        contentDescription = "Favourite"
                    )
                }
            }
            Text(
                text = "${bookUiModel.pages} pages",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = buildString {
                    if (bookUiModel.authors.size == 1) append("Author: ") else append("Authors: ")
                    bookUiModel.authors.map { author ->
                        append(author)
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = bookUiModel.genres, key = { genre -> genre.id }) { genre ->
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraLarge)
                            .border(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                width = 1.dp,
                                shape = MaterialTheme.shapes.extraLarge
                            ), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = genre.name.untangle("-"),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Description",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = bookUiModel.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
        CLibButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            onClick = { onReserve(bookUiModel.id) }
        ) {
            Text(
                text = stringResource(id = R.string.reserve_now),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun ErrorScreen(
    message: String, onRetry: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
        Column(modifier = Modifier.padding(start = 24.dp)) {
            Text(
                text = message, style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            CLibOutlinedButton(onClick = { onRetry() }) {
                Text(
                    text = "Retry"
                )
            }
        }
    }
}