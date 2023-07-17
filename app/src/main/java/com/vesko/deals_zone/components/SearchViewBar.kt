package com.vesko.deals_zone.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vesko.deals_zone.viewmodel.DealsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchViewBar(dealsViewModel: DealsViewModel, navigateOnCardClick: (String) -> Unit) {
    val dealUiState by dealsViewModel.uiState.collectAsState()
    var active by remember { mutableStateOf(false) }
    var searchTapped by remember { mutableStateOf(false) }

    val horizontalPadding: Dp by animateDpAsState(
        if (!active) {
            16.dp
        } else {
            0.dp
        }
    )

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding),
        query = dealUiState.searchBarText,
        onQueryChange = {
            searchTapped = false
            dealsViewModel.onSearchDeal(it)
        },
        onSearch = { searchTapped = true },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text(text = "Search for deal...") },
        leadingIcon = {
            if (active) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back icon",
                    Modifier.clickable {
                        active = false
                        searchTapped = false
                        dealsViewModel.onSearchDeal("")
                    }
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon"
                )
            }
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (dealUiState.searchBarText.isEmpty()) {
                            active = false
                            searchTapped = false
                        } else {
                            searchTapped = false
                            dealsViewModel.onSearchDeal("")
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "close icon"
                )
            }
        }
    ) {
        BackHandler { // handle back pressed
            active = false
            searchTapped = false
            dealsViewModel.onSearchDeal("")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .then(
                    if (searchTapped) {
                        Modifier.padding(start = 16.dp, end = 16.dp, bottom = 80.dp)
                    } else {
                        Modifier.padding(bottom = 80.dp)
                    }
                )
        ) {
            items(dealUiState.filteredList.take(10)) { deal ->
                if (searchTapped) {
                    DealItem(
                        deal = deal,
                        favoriteDeal = false,
                        snackbarHostState = null,
                        bottomBarVisible = {},
                        navigateOnCardClick = {dealId ->
                            dealsViewModel.onSearchDeal("")
                            navigateOnCardClick(dealId)
                        },
                        onClickFavoriteItem = {},
                        searchViewItemList = true,
                    )
                } else {
                    Row(modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            dealsViewModel.onSearchDeal("")
                            navigateOnCardClick(deal.id)
                        }) {
                        Text(text = deal.title, overflow = TextOverflow.Ellipsis, maxLines = 1)
                    }
                    Divider()
                }
            }
        }
    }
}