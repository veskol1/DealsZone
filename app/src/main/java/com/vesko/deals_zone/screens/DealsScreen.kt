package com.vesko.deals_zone.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vesko.deals_zone.components.LazyColumnList
import com.vesko.deals_zone.components.SearchViewBar
import com.vesko.deals_zone.utils.mockDealUiState
import com.vesko.deals_zone.viewmodel.DealsViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DealsScreen(
    dealsViewModel: DealsViewModel?,
    dealUiState: DealsViewModel.UiState,
    navigateOnCardClick: (String) -> Unit,
    bottomBarVisible: (Boolean) -> Unit,
    snackbarHostState: SnackbarHostState
) {

    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchViewBar(dealUiState = dealUiState,
            onSearchDeal = { searchText ->
                dealsViewModel?.onSearchDeal(searchText)
            },
            navigateOnCardClick = { dealId ->
                navigateOnCardClick(dealId)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        when (dealUiState.status) {
            DealsViewModel.Status.LOADING -> LoadingScreen()
            DealsViewModel.Status.ERROR -> ErrorScreen()
            else -> {
                LazyColumnList(
                    itemsToShow = dealUiState.numItemsToShow,
                    dealList = dealUiState.list,
                    dealFavoriteList = dealUiState.favoriteSavedDeals,
                    navigateOnCardClick = { dealId ->
                        navigateOnCardClick(dealId)
                    },
                    bottomBarVisible = bottomBarVisible,
                    snackbarHostState = snackbarHostState,
                    onClickFavoriteItem = { dealId ->
                        dealsViewModel?.updateFavoriteDealsState(dealId = dealId)
                        scope.launch {
                            dealsViewModel?.saveToDataStore(id = dealId)
                        }
                    },
                    loadMoreItems = {
                        dealsViewModel?.loadMoreItems()
                    },
                    showAds = false
                )
            }
        }
    }
}

@Composable
@Preview (backgroundColor = 0xFFFFFFFF, showBackground = true)
fun DealsScreenPreview() {
    val snackbarHostState = remember { SnackbarHostState() }
    DealsScreen(
        dealsViewModel = null,
        dealUiState = mockDealUiState,
        navigateOnCardClick = {},
        bottomBarVisible = { true },
        snackbarHostState = snackbarHostState)
}