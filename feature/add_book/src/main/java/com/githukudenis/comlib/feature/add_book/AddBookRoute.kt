
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
package com.githukudenis.comlib.feature.add_book

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.githukudenis.comlib.core.common.capitalize
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibTextButton
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibLoadingDialog
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.CLibLoadingSpinner
import com.githukudenis.comlib.core.designsystem.ui.components.text_fields.CLibOutlinedTextField
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookRoute(
    onNavigateUp: () -> Unit,
    onBookAdded: () -> Unit,
    viewModel: AddBookViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val imagePickLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.onEvent(AddBookUiEvent.OnChangePhoto(uri))
            } else {
                Toast.makeText(context, context.getString(R.string.no_media_selected), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    val currentOnFinishAddingBook by rememberUpdatedState(onBookAdded)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.add_book_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    CLibTextButton(onClick = { viewModel.onEvent(AddBookUiEvent.OnSave) }) {
                        Text(text = stringResource(R.string.save_book))
                    }
                }
            )
        }
    ) { paddingValues ->
        LaunchedEffect(state.message) {
            if (state.message.isNotEmpty()) {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.onEvent(AddBookUiEvent.DismissMessage)
            }
        }

        LaunchedEffect(state.isSuccess) {
            if (state.isSuccess) {
                viewModel.onEvent(
                    AddBookUiEvent.ShowMessage(context.getString(R.string.book_added_successfully))
                )
                delay(3000)
                currentOnFinishAddingBook()
            }
        }

        var bottomSheetExpanded by remember { mutableStateOf(false) }

        if (state.isLoading) {
            CLibLoadingDialog(label = stringResource(R.string.saving_book), onDismissRequest = {})
        }

        if (bottomSheetExpanded) {
            ModalBottomSheet(onDismissRequest = { bottomSheetExpanded = !bottomSheetExpanded }) {
                when (val genreState = state.genreState) {
                    is GenreUiState.Error -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(LocalDimens.current.large)
                        ) {
                            Text(text = genreState.message)
                            CLibButton(onClick = { viewModel.onEvent(AddBookUiEvent.OnRetryLoadGenres) }) {
                                Text(
                                    text = stringResource(R.string.retry),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                    GenreUiState.Loading -> {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            CLibLoadingSpinner()
                        }
                    }
                    is GenreUiState.Success -> {
                        LazyColumn {
                            items(genreState.genres) { genre ->
                                Row(
                                    modifier =
                                        Modifier.fillMaxWidth()
                                            .clickable(
                                                onClick = {
                                                    viewModel.onEvent(AddBookUiEvent.OnGenreChange(genre))
                                                    //                                            bottomSheetExpanded = false
                                                }
                                            )
                                            .padding(LocalDimens.current.medium),
                                    horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.medium),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = genre.id == state.selectedGenre.id,
                                        onClick = { viewModel.onEvent(AddBookUiEvent.OnGenreChange(genre)) }
                                    )
                                    Text(
                                        text = genre.name.capitalize().split("-").joinToString(" "),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        LazyColumn(
            modifier =
                Modifier.fillMaxSize()
                    .windowInsetsPadding(WindowInsets.ime)
                    .padding(paddingValues)
                    .padding(
                        start = LocalDimens.current.extraLarge,
                        end = LocalDimens.current.extraLarge,
                        bottom = LocalDimens.current.extraLarge
                    ),
            verticalArrangement = Arrangement.spacedBy(LocalDimens.current.medium)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(LocalDimens.current.medium),
                    horizontalArrangement = Arrangement.Center
                ) {
                    BookImage(
                        imageUri = state.photoUri,
                        onPickImage = {
                            imagePickLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )
                }
            }
            item { Text(text = "Details", style = MaterialTheme.typography.titleMedium) }
            item {
                AddBookFormItem(
                    title = "Title",
                    value = state.title,
                    onValueChange = { viewModel.onEvent(AddBookUiEvent.OnTitleChange(it)) }
                )
            }
            item {
                AddBookFormItem(
                    title = "Author(s)",
                    placeholder = stringResource(R.string.author_placeholder),
                    value = state.authors,
                    onValueChange = { viewModel.onEvent(AddBookUiEvent.OnAuthorChange(it)) }
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.extraLarge)
                ) {
                    AddBookFormItem(
                        modifier = Modifier.weight(1f),
                        title = "Edition",
                        value = state.edition,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { viewModel.onEvent(AddBookUiEvent.OnEditionChange(it)) }
                    )
                    AddBookFormItem(
                        modifier = Modifier.weight(1f),
                        title = "Pages",
                        value = state.pages,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { viewModel.onEvent(AddBookUiEvent.OnPageChange(it)) }
                    )
                }
            }
            item {
                AddBookFormItem(
                    title = "Genre",
                    readOnly = true,
                    value = state.selectedGenre.name.capitalize().split("-").joinToString(" "),
                    trailingIcon = {
                        IconButton(onClick = { bottomSheetExpanded = !bottomSheetExpanded }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = stringResource(R.string.select_genre)
                            )
                        }
                    },
                    onValueChange = {}
                )
            }
            item {
                AddBookFormItem(
                    singleLine = false,
                    maxLines = 10,
                    supportingText =
                        if (!state.descriptionIsValid) {
                            "${state.description.length}/200"
                        } else {
                            null
                        },
                    minLines = 4,
                    title = "Description",
                    value = state.description,
                    onValueChange = { viewModel.onEvent(AddBookUiEvent.OnDescriptionChange(it)) }
                )
            }
        }
    }
}

@Composable
fun BookImage(imageUri: Uri? = null, onPickImage: () -> Unit) {
    Box {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp).clip(CircleShape),
                contentDescription = "Book image"
            )
        } else {
            Box(modifier = Modifier.size(100.dp).clip(CircleShape).background(Color.LightGray))
        }
        Box(
            modifier =
                Modifier.align(Alignment.BottomEnd)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .border(width = 1.dp, shape = CircleShape, color = Color.LightGray)
                    .clickable { onPickImage() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.change_book_image),
                tint = Color.LightGray
            )
        }
    }
}

@Composable
fun AddBookFormItem(
    modifier: Modifier = Modifier.fillMaxWidth(),
    singleLine: Boolean = true,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    supportingText: String? = null,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    title: String,
    value: String,
    placeholder: String? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    onValueChange: (String) -> Unit
) {

    CLibOutlinedTextField(
        modifier = modifier,
        singleLine = singleLine,
        value = value,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        supportingText = {
            if (supportingText != null) {
                Text(text = supportingText, style = MaterialTheme.typography.labelSmall)
            }
        },
        minLines = minLines,
        trailingIcon = trailingIcon,
        onValueChange = onValueChange,
        label = title,
        placeholder = {
            if (placeholder != null) {
                Text(text = placeholder, style = MaterialTheme.typography.labelSmall)
            }
        }
    )
}
