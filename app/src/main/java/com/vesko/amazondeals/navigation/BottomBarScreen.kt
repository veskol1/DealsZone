package com.vesko.amazondeals.navigation

import androidx.annotation.DrawableRes
import com.example.amazondeals.R

sealed class BottomBarScreen(
    val route: String,
    val name: String,
    @DrawableRes val icon : Int,
    @DrawableRes val selectedIcon : Int
) {
    object Deals: BottomBarScreen(
        route = "deals",
        name = "Deals",
        icon = R.drawable.list_icon,
        selectedIcon = R.drawable.list_icon_filled
    )

    object Category: BottomBarScreen(
        route = "category",
        name = "Categories",
        icon = R.drawable.category_icon,
        selectedIcon = R.drawable.category_icon_filled
    )
    object Favorites: BottomBarScreen(
        route = "favorites",
        name = "Favorites",
        icon = R.drawable.favorite_icon,
        selectedIcon = R.drawable.favorite_icon_filled
    )

    object Settings: BottomBarScreen(
        route = "settings",
        name = "Settings",
        icon = R.drawable.settings_icon,
        selectedIcon = R.drawable.settings_icon_filled
    )
}