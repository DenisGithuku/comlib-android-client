package com.githukudenis.comlib.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.feature.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCard(
    book: Book, onClick: (String) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.width(IntrinsicSize.Min),
        onClick = { onClick(book.id) }) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                modifier = Modifier
                    .sizeIn(
                        maxWidth = 150.dp,
                        minWidth = 150.dp,
                        maxHeight = 100.dp,
                        minHeight = 100.dp
                    )
                    .clip(MaterialTheme.shapes.large),
                model = "https://comlib-api.onrender.com/img/books/${book.image}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = buildString {
                        book.authors.forEach { author ->
                            append(author)
                        }
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.6f
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                CLibButton(
                    shape = MaterialTheme.shapes.extraLarge,
                    onClick = {},
                ) {
                    Text(
                        text = stringResource(R.string.reserve),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
        }
    }
}