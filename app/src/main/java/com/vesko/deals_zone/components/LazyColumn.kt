package com.vesko.deals_zone.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vesko.deals_zone.model.Deal

@Composable
fun LazyColumnList(
    dealList: ArrayList<Deal>,
    dealFavoriteList: ArrayList<Deal>,
    navigateOnCardClick: (String) -> Unit,
    bottomBarVisible: (Boolean) -> Unit,
    snackbarHostState: SnackbarHostState,
    onClickFavoriteItem: (String) -> Unit,
    showWithoutBottomNavBar: Boolean = false
) {
    LazyColumn(
        modifier = Modifier.padding(
            top = 0.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 0.dp.takeIf { showWithoutBottomNavBar } ?: 80.dp
        )
    ) {
        items(dealList.take(20)) { deal ->
            DealItem(
                deal = deal,
                favoriteDeal = dealFavoriteList.contains(deal),
                snackbarHostState = snackbarHostState,
                bottomBarVisible = bottomBarVisible,
                navigateOnCardClick = { dealId ->
                    navigateOnCardClick(dealId)
                },
                onClickFavoriteItem = { dealId ->
                    onClickFavoriteItem(dealId)
                },
                searchViewItemList = false,
            )
        }
    }
}