package com.vesko.amazondeals.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vesko.amazondeals.components.BottomNavGraph
import com.vesko.amazondeals.utils.BottomBarScreen
import com.vesko.amazondeals.viewmodel.DealsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(dealsViewModel: DealsViewModel = viewModel()) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    var showBar by remember {
        mutableStateOf(true)
    }
    Scaffold(
        bottomBar = {
            if (showBar) {
                BottomNavigation(navController = navController)
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        BottomNavGraph(
            navController = navController,
            dealsViewModel = dealsViewModel,
            snackbarHostState = snackbarHostState
        ) { bottomBarVisible ->
            showBar = bottomBarVisible
        }
    }
}

@Composable
fun BottomNavigation (navController: NavController) {
    val items = listOf(BottomBarScreen.Deals, BottomBarScreen.Category, BottomBarScreen.Settings)
    NavigationBar {
        items.forEach { screen ->
            Item(item = screen, navController = navController)
        }
    }
}

@Composable
fun RowScope.Item(item: BottomBarScreen, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBarItem(
        icon = { Icon( ImageVector.vectorResource(id = item.icon), contentDescription = null) },
        label = { Text(item.name) },
        selected = currentDestination?.route == item.route,
        onClick = {
            navController.navigate(item.route) {
                popUpTo(BottomBarScreen.Deals.route) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

