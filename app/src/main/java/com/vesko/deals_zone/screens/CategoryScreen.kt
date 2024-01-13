package com.vesko.deals_zone.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.sp
import com.vesko.deals_zone.components.LazyColumnList
import com.vesko.deals_zone.components.TopBar
import com.vesko.deals_zone.viewmodel.DealsViewModel
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    categoryName: String,
    navigateOnCardClick: (String) -> Unit,
    bottomBarVisible: (Boolean) -> Unit,
    dealsViewModel: DealsViewModel,
    snackbarHostState: SnackbarHostState,
    onBackClicked: () -> Unit
) {
    val dealUiState by dealsViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopBar(title = categoryName, onBackClicked = onBackClicked) }
    ) {
        BackHandler { // handle back pressed
            onBackClicked()
        }
        val foundDeals = dealUiState.dealsByCategory.isEmpty()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center.takeIf { foundDeals } ?: Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (dealUiState.dealsByCategory.isEmpty()) {
                Text(
                    text = "No deals yet\n Try again later :)",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
            } else {
                LazyColumnList(
                    itemsToShow = dealUiState.dealsByCategory.size ,
                    dealList = dealUiState.dealsByCategory,
                    dealFavoriteList = dealUiState.favoriteSavedDeals,
                    navigateOnCardClick = { dealId ->
                        navigateOnCardClick(dealId)
                    },
                    bottomBarVisible = bottomBarVisible,
                    snackbarHostState = snackbarHostState,
                    onClickFavoriteItem = { dealId ->
                        dealsViewModel.updateFavoriteDealsState(dealId = dealId)
                        scope.launch {
                            dealsViewModel.saveToDataStore(id = dealId)
                        }
                    },
                    showWithoutBottomNavBar = true
                )
            }
        }
    }
}
