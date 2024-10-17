
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
package com.githukudenis.comlib.feature.book_detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.core.common.untangle
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibOutlinedButton
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibLoadingDialog
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.loadingBrush

@Composable
fun BookDetailRoute(viewModel: BookDetailViewModel = hiltViewModel(), onBackPressed: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        onToggleFavourite = { bookId -> viewModel.toggleBookmark(bookId) },
        onRetry = {
            //            viewModel.onRetry()
        },
        onBackPressed = onBackPressed,
        onReserve = { bookId -> viewModel.onReserveBook(bookId) },
        onUnReserve = { bookId -> viewModel.onUnReserveBook(bookId) },
        onUserMessageShown = { messageId -> viewModel.onUserMessageShown(messageId) }
    )
}

@Composable
fun BookDetailScreen(
    state: BookDetailUiState,
    onToggleFavourite: (String) -> Unit,
    onRetry: () -> Unit,
    onBackPressed: () -> Unit,
    onReserve: (String) -> Unit,
    onUnReserve: (String) -> Unit,
    onUserMessageShown: (Int) -> Unit
) {
    when (state) {
        is BookDetailUiState.Loading -> {
            LoadingScreen(onBackPressed = onBackPressed)
        }
        is BookDetailUiState.Success -> {
            LoadedScreen(
                bookUiModel = state.bookUiModel,
                userMessages = state.userMessages,
                isUpdating = state.updating,
                onBackPressed = onBackPressed,
                onToggleFavourite = onToggleFavourite,
                onReserve = onReserve,
                onUnReserve = onUnReserve,
                onUserMessageShown = onUserMessageShown
            )
        }
        is BookDetailUiState.Error -> {
            ErrorScreen(message = state.message, onRetry = onRetry)
        }
    }
}

@Composable
fun LoadingScreen(onBackPressed: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(modifier = Modifier.fillMaxWidth().height(250.dp).background(brush = loadingBrush())) {
            Box(
                modifier =
                    Modifier.safeDrawingPadding()
                        .padding(horizontal = 16.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
                        .clickable { onBackPressed() }
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier =
                    Modifier.height(16.dp)
                        .fillMaxWidth(0.4f)
                        .clip(CircleShape)
                        .background(brush = loadingBrush())
            )
            Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(brush = loadingBrush()))
        }
        Box(
            modifier =
                Modifier.height(16.dp)
                    .fillMaxWidth(0.6f)
                    .padding(horizontal = 16.dp)
                    .clip(CircleShape)
                    .background(brush = loadingBrush())
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            (0..2).map {
                Box(
                    modifier =
                        Modifier.height(40.dp).width(70.dp).clip(CircleShape).background(brush = loadingBrush())
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )
        Box(
            modifier =
                Modifier.height(16.dp)
                    .fillMaxWidth(0.4f)
                    .clip(CircleShape)
                    .padding(horizontal = 16.dp)
                    .background(brush = loadingBrush())
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            (0..5).map {
                Box(
                    modifier =
                        Modifier.height(16.dp)
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .background(brush = loadingBrush())
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoadedScreen(
    userMessages: List<UserMessage>,
    bookUiModel: BookUiModel,
    isUpdating: Boolean,
    onUserMessageShown: (Int) -> Unit,
    onBackPressed: () -> Unit,
    onToggleFavourite: (String) -> Unit,
    onReserve: (String) -> Unit,
    onUnReserve: (String) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(userMessages) {
        if (userMessages.isNotEmpty()) {
            val userMessage = userMessages.first()
            Toast.makeText(context, userMessage.message, Toast.LENGTH_SHORT).show()
            onUserMessageShown(userMessage.id)
        }
    }

    if (isUpdating) {
        CLibLoadingDialog()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier) {
                AsyncImage(
                    model = bookUiModel.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentScale = ContentScale.FillWidth
                )
                Box(
                    modifier =
                        Modifier.safeDrawingPadding()
                            .padding(horizontal = 16.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
                            .clickable { onBackPressed() }
                ) {
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.background
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = bookUiModel.title, style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { onToggleFavourite(bookUiModel.id) }) {
                    Icon(
                        imageVector =
                            if (bookUiModel.isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        tint =
                            if (bookUiModel.isFavourite) {
                                Color.Yellow.copy(green = 0.7f)
                            } else MaterialTheme.colorScheme.onBackground,
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
                text =
                    buildString {
                        if (bookUiModel.authors.size == 1) append("Author: ") else append("Authors: ")
                        bookUiModel.authors.map { author -> append(author) }
                    },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Genre",
                style = MaterialTheme.typography.titleMedium
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (genre in bookUiModel.genres) {
                    Box(
                        modifier =
                            Modifier.clip(MaterialTheme.shapes.extraLarge)
                                .border(
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                                    width = 1.dp,
                                    shape = MaterialTheme.shapes.extraLarge
                                ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = genre.name.untangle("-"),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
            HorizontalDivider(
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
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
        CLibButton(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(16.dp),
            onClick = {
                if (!bookUiModel.isReserved) {
                    onReserve(bookUiModel.id)
                } else {
                    onUnReserve(bookUiModel.id)
                }
            }
        ) {
            Text(
                text =
                    stringResource(
                        id = if (!bookUiModel.isReserved) R.string.reserve_now else R.string.unreserve_now
                    ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
        Column(modifier = Modifier.padding(start = 24.dp)) {
            Text(text = message, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(12.dp))
            CLibOutlinedButton(onClick = { onRetry() }) { Text(text = "Retry") }
        }
    }
}
