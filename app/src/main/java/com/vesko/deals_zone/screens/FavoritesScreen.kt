package com.vesko.deals_zone.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vesko.deals_zone.components.LazyColumnList
import com.vesko.deals_zone.viewmodel.DealsViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    navigateOnCardClick: (String) -> Unit,
    bottomBarVisible: (Boolean) -> Unit,
    dealsViewModel: DealsViewModel,
    snackbarHostState: SnackbarHostState
) {
    val dealUiState by dealsViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Favorite Deals",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 12.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (dealUiState.favoriteSavedDeals.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No deals saved yet",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }

        } else {
            LazyColumnList(
                itemsToShow = dealUiState.favoriteSavedDeals.size,
                dealList = dealUiState.favoriteSavedDeals,
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
                }
            )
        }
    }
}