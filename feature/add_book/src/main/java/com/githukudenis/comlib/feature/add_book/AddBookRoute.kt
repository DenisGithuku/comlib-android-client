
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens

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
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier.fillMaxSize()
                    .windowInsetsPadding(WindowInsets.ime)
                    .padding(paddingValues)
                    .padding(horizontal = LocalDimens.current.extraLarge),
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
                AddBookFormItem(
                    title = "Author",
                    value = state.author,
                    onValueChange = { viewModel.onEvent(AddBookUiEvent.OnAuthorChange(it)) }
                )
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
                        title = "Year",
                        value = state.year,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { viewModel.onEvent(AddBookUiEvent.OnYearChange(it)) }
                    )
                }
                AddBookFormItem(
                    title = "Genre",
                    value = state.genre,
                    onValueChange = { viewModel.onEvent(AddBookUiEvent.OnGenreChange(it)) }
                )
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
            item {
                CLibButton(modifier = Modifier.fillMaxWidth(), onClick = {}) {
                    Text(text = "Save", style = MaterialTheme.typography.labelLarge)
                }
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
    supportingText: String? = null,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        singleLine = singleLine,
        value = value,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        supportingText = {
            if (supportingText != null) {
                Text(text = supportingText, style = MaterialTheme.typography.labelSmall)
            }
        },
        minLines = minLines,
        onValueChange = onValueChange,
        label = { Text(text = title, style = MaterialTheme.typography.labelSmall) }
    )
}
