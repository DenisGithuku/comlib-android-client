package com.githukudenis.comlib.feature.my_books

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MyBooksRoute(
    onNavigateToBookDetails: (String) -> Unit,
    onNavigateToAddBook: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Your books appear here",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}