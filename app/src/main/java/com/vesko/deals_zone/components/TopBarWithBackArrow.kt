package com.vesko.deals_zone.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.vesko.deals_zone.utils.mockDealsList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, onBackClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = title,maxLines = 1, overflow = TextOverflow.Clip) },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back icon")
            }
        }
    )
}

@Composable
@Preview
fun TopBarPreview() {
    TopBar(title = mockDealsList[0].title, onBackClicked = {})
}