package com.vesko.deals_zone.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, onBackClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = title,maxLines = 2, overflow = TextOverflow.Ellipsis) },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back icon")
            }
        }
    )
}