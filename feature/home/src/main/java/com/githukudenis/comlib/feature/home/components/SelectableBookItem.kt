package com.githukudenis.comlib.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.model.book.Book

@Composable
fun SelectableBookItem(
    book: Book,
    isSelected: Boolean,
    onSelected: () -> Unit,
) {
    Row(
        modifier = Modifier.clickable { onSelected() },
        verticalAlignment = Alignment.CenterVertically
        ) {
        RadioButton(selected = isSelected, onClick = onSelected)
        Spacer(modifier = Modifier.width(12.dp))
        AsyncImage(
            model = "https://comlib-api.onrender.com/img/books/${book.image}",
            contentDescription = "Book image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = book.title,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "${book.pages} pages"
            )
        }
    }
}