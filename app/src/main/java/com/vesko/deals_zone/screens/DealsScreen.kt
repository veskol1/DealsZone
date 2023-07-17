package com.vesko.deals_zone.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vesko.deals_zone.components.DealItem
import com.vesko.deals_zone.components.SearchViewBar
import com.vesko.deals_zone.model.Deal
import com.vesko.deals_zone.viewmodel.DealsViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DealsScreen(
    navigateOnCardClick: (String) -> Unit,
    bottomBarVisible: (Boolean) -> Unit,
    dealsViewModel: DealsViewModel,
    snackbarHostState: SnackbarHostState
) {
    val dealUiState by dealsViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchViewBar(dealsViewModel = dealsViewModel, navigateOnCardClick = { dealId ->
            navigateOnCardClick(dealId)
        })
        Spacer(modifier = Modifier.height(8.dp))
        when (dealUiState.status) {
            DealsViewModel.Status.LOADING -> LoadingScreen()
            DealsViewModel.Status.ERROR -> ErrorScreen()
            else -> {
                ShowLazyColumn(
                    dealList = dealUiState.list,
                    dealFavoriteList = dealUiState.favoriteSavedDeals,
                    navigateOnCardClick = { dealId ->
                        navigateOnCardClick(dealId)
                    },
                    bottomBarVisible = bottomBarVisible,
                    snackbarHostState = snackbarHostState,
                    onClickFavoriteItem = {dealId ->
                        dealsViewModel.updateFavoriteDealsState(dealId = dealId)
                        scope.launch {
                            dealsViewModel.saveToDataStore(context, id = dealId)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ShowLazyColumn(
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

@Composable
@Preview (backgroundColor = 0xFFFFFFFF, showBackground = true)
fun DealsScreenPreview() {
    //DealsScreen()
}