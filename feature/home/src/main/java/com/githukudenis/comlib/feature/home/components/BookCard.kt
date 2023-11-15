package com.githukudenis.comlib.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.feature.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCard(
    book: Book, onClick: (String) -> Unit
) {
    Card(onClick = { onClick(book.id) }) {
        Column(
            modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 120.dp)
                    .clip(MaterialTheme.shapes.medium),
                model = "https://comlib-api.onrender.com/img/books/${book.image}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = buildString {
                    book.authors.forEach { author ->
                        append(author)
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.6f
                )
            )
        }
    }
}