
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
package com.githukudenis.comlib.feature.books.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.common.capitalize
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.feature.books.BookItemUiModel
import com.githukudenis.comlib.feature.books.R

@Composable
fun BookComponent(bookItemUiModel: BookItemUiModel, onOpenBookDetails: (String) -> Unit) {
    Row(
        modifier =
            Modifier.fillMaxWidth()
                .clickable { onOpenBookDetails(bookItemUiModel.id) }
                .padding(LocalDimens.current.extraLarge),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            AsyncImage(
                modifier = Modifier.size(50.dp).clip(CircleShape),
                model = bookItemUiModel.imageUrl,
                contentDescription = stringResource(R.string.book_image),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = bookItemUiModel.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text =
                        buildString {
                            bookItemUiModel.authors.forEachIndexed { index, author ->
                                append(author.capitalize())
                                if (index != bookItemUiModel.authors.lastIndex) append("\u2022")
                            }
                        },
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        IconButton(onClick = { onOpenBookDetails(bookItemUiModel.id) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = "Open book details",
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}
