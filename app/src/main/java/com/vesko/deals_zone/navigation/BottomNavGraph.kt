package com.vesko.deals_zone.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vesko.deals_zone.screens.CategoriesScreen
import com.vesko.deals_zone.screens.CategoryScreen
import com.vesko.deals_zone.screens.DealScreen
import com.vesko.deals_zone.screens.DealsScreen
import com.vesko.deals_zone.screens.SettingsScreen
import com.vesko.deals_zone.screens.FavoritesScreen
import com.vesko.deals_zone.viewmodel.DealsViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    dealsViewModel: DealsViewModel,
    snackbarHostState: SnackbarHostState,
    bottomBarVisible: (Boolean) -> Unit
) {
    val dealUiState by dealsViewModel.uiState.collectAsState()

    NavHost(
        navController,
        startDestination = BottomBarScreen.Deals.route
    ) {
        composable(route = BottomBarScreen.Deals.route) {
            DealsScreen(
                dealsViewModel = dealsViewModel,
                dealUiState = dealUiState,
                navigateOnCardClick = { dealId ->
                    navController.navigate("deal/$dealId/true")
                },
                bottomBarVisible = bottomBarVisible,
                snackbarHostState = snackbarHostState
            )
        }

        composable(route = BottomBarScreen.Category.route) {
            CategoriesScreen(bottomBarVisible = bottomBarVisible,onCategorySelected = { category ->
                dealsViewModel.updateCategoryDeals(category = category.name)
                navController.navigate("category/$category")
            })
        }

        composable(route = BottomBarScreen.Favorites.route)  {
            FavoritesScreen(
                navigateOnCardClick = { dealId ->
                    navController.navigate("deal/$dealId/true")
                },
                bottomBarVisible = bottomBarVisible,
                dealsViewModel = dealsViewModel,
                snackbarHostState = snackbarHostState
            )
        }

        composable(route = BottomBarScreen.Settings.route)  {
            SettingsScreen()
        }

        composable(
            route = "deal/{id}/{showBottomBar}"
        ) {
            val dealId = it.arguments?.getString("id") ?: ""
            val showBottomBar = it.arguments?.getString("showBottomBar") ?: ""
            DealScreen(
                deal = dealsViewModel.findDeal(dealId),
                onBackClicked = {
                    navController.popBackStack()
                    bottomBarVisible(showBottomBar == "true")
                })
        }

        composable(route = "category/{categoryName}") {
            val categoryName =  it.arguments?.getString("categoryName") ?: ""
            CategoryScreen(
                categoryName = categoryName,
                navigateOnCardClick = { dealId ->
                    navController.navigate("deal/$dealId/false")
                },
                bottomBarVisible = bottomBarVisible,
                dealsViewModel = dealsViewModel,
                snackbarHostState = snackbarHostState,
                onBackClicked = {
                    navController.popBackStack()
                    bottomBarVisible(true)
                }
            )
        }
    }
}