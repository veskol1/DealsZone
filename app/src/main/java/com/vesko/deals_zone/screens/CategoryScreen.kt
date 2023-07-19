package com.vesko.deals_zone.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopBar(title = categoryName, onBackClicked = onBackClicked) }
    ) {
        BackHandler { // handle back pressed
            onBackClicked()
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            LazyColumnList(
                dealList = dealUiState.dealsByCategory  ,
                dealFavoriteList = dealUiState.favoriteSavedDeals,
                navigateOnCardClick = { dealId ->
                    navigateOnCardClick(dealId)
                },
                bottomBarVisible = bottomBarVisible,
                snackbarHostState = snackbarHostState,
                onClickFavoriteItem = { dealId ->
                    dealsViewModel.updateFavoriteDealsState(dealId = dealId)
                    scope.launch {
                        dealsViewModel.saveToDataStore(context, id = dealId)
                    }
                },
                showWithoutBottomNavBar = true
            )
        }
    }
}
