package com.vesko.amazondeals.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vesko.amazondeals.components.DealItem
import com.vesko.amazondeals.components.SearchViewBar
import com.vesko.amazondeals.viewmodel.DealsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DealsScreen(
    navController: NavController,
    bottomBarVisible: (Boolean) -> Unit,
    dealsViewModel: DealsViewModel,
    snackbarHostState: SnackbarHostState
) {
    val dealUiState by dealsViewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchViewBar(dealsViewModel, navigateOnCardClick = { route ->
            navController.navigate(route)
        })
        Spacer(modifier = Modifier.height(8.dp))
        when (dealUiState.status) {
            DealsViewModel.Status.LOADING ->  LoadingScreen()
            DealsViewModel.Status.ERROR -> ErrorScreen()
            else -> {
                ShowLazyColumn(
                    dealUiState = dealUiState,
                    navController = navController,
                    bottomBarVisible = bottomBarVisible,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}

@Composable
fun ShowLazyColumn(
    dealUiState: DealsViewModel.UiState,
    navController: NavController,
    bottomBarVisible: (Boolean) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    LazyColumn(
        modifier = Modifier.padding(
            top = 0.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 80.dp
        )
    ) {
        items(dealUiState.list.take(20)) { deal ->
            DealItem(
                deal = deal,
                snackbarHostState = snackbarHostState,
                bottomBarVisible = bottomBarVisible,
                searchView = false,
                navigateOnCardClick = { route ->
                    navController.navigate(route)
                }
            )
        }
    }
}

@Composable
@Preview (backgroundColor = 0xFFFFFFFF, showBackground = true)
fun DealsScreenPreview() {
    //DealsScreen()
}