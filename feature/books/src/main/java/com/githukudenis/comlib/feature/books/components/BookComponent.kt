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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.githukudenis.comlib.feature.books.BookItemUiModel

@Composable
fun BookComponent(bookItemUiModel: BookItemUiModel, onOpenBookDetails: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onOpenBookDetails(bookItemUiModel.id)
            }
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                model = "https://comlib-api.onrender.com/img/books/${bookItemUiModel.imageUrl}",
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = bookItemUiModel.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = buildString {
                        bookItemUiModel.authors.map { author ->
                            append(author)
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        IconButton(onClick = { onOpenBookDetails(bookItemUiModel.id) }) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open book details",
            )
        }
    }
}