package com.vesko.deals_zone.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vesko.deals_zone.components.LazyColumnList
import com.vesko.deals_zone.components.SearchViewBar
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
                LazyColumnList(
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
                            dealsViewModel.saveToDataStore(id = dealId)
                        }
                    },
                    showAds = true
                )
            }
        }
    }
}

@Composable
@Preview (backgroundColor = 0xFFFFFFFF, showBackground = true)
fun DealsScreenPreview() {
    //DealsScreen()
}