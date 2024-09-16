
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
package com.githukudenis.comlib.feature.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.common.capitalize
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.feature.home.BookUiModel
import com.githukudenis.comlib.feature.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCard(
    bookUiModel: BookUiModel,
    onClick: (String) -> Unit,
    onReserve: (String) -> Unit,
    onToggleFavourite: (String) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border =
            BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)),
        modifier = Modifier.width(150.dp),
        onClick = { onClick(bookUiModel.book.id) }
    ) {
        Column {
            AsyncImage(
                modifier =
                    Modifier.sizeIn(
                            minHeight = 120.dp,
                            minWidth = 150.dp,
                            maxHeight = 120.dp,
                            maxWidth = 150.dp
                        )
                        .clip(
                            RoundedCornerShape(
                                topStart = 6.dp,
                                topEnd = 6.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        ),
                model = bookUiModel.book.image,
                contentDescription = stringResource(id = R.string.book_image),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = bookUiModel.book.title,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(0.7f)
                    )
                    IconButton(
                        onClick = { onToggleFavourite(bookUiModel.book.id) },
                        modifier = Modifier.weight(0.3f)
                    ) {
                        Icon(
                            imageVector =
                                if (bookUiModel.isFavourite) {
                                    Icons.Filled.Favorite
                                } else Icons.Outlined.FavoriteBorder,
                            contentDescription = stringResource(R.string.toggle_favourite),
                            tint =
                                if (bookUiModel.isFavourite) {
                                    Color(0xFFFFAA00)
                                } else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                    }
                }
                Text(
                    modifier = Modifier,
                    text =
                        buildString {
                            bookUiModel.book.authors.forEachIndexed { index, author ->
                                append(author.capitalize())
                                if (index != bookUiModel.book.authors.lastIndex) append("\u2022")
                            }
                        },
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = bookUiModel.book.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
                CLibButton(
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onReserve(bookUiModel.book.id) }
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = stringResource(R.string.reserve),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}
