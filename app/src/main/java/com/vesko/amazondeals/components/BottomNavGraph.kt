package com.vesko.amazondeals.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vesko.amazondeals.screens.CategoryScreen
import com.vesko.amazondeals.screens.DealScreen
import com.vesko.amazondeals.screens.DealsScreen
import com.vesko.amazondeals.screens.SettingsScreen
import com.vesko.amazondeals.utils.BottomBarScreen
import com.vesko.amazondeals.viewmodel.DealsViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    dealsViewModel: DealsViewModel,
    snackbarHostState: SnackbarHostState,
    bottomBarVisible: (Boolean) -> Unit
) {
    NavHost(
        navController,
        startDestination = BottomBarScreen.Deals.route
    ) {
        composable(BottomBarScreen.Deals.route) {
            DealsScreen(
                navController = navController,
                bottomBarVisible = bottomBarVisible,
                dealsViewModel = dealsViewModel,
                snackbarHostState = snackbarHostState
            )
        }

        composable(BottomBarScreen.Category.route) {
            CategoryScreen()
        }

        composable(BottomBarScreen.Settings.route)  {
            SettingsScreen()
        }

        composable("deal/{id}") {
            DealScreen(id = it.arguments?.getString("id") ?: "", onBackClicked = {
                navController.popBackStack()
                bottomBarVisible(true)
            })
        }
    }
}