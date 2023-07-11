package com.vesko.amazondeals.utils

import androidx.annotation.DrawableRes
import com.example.amazondeals.R

sealed class BottomBarScreen(
    val route: String,
    val name: String,
    @DrawableRes val icon : Int
) {
    object Deals: BottomBarScreen(
        route = "deals",
        name = "Deals",
        icon = R.drawable.list
    )

    object Category: BottomBarScreen(
        route = "category",
        name = "Categories",
        icon = R.drawable.category
    )

    object Settings: BottomBarScreen(
        route = "settings",
        name = "Settings",
        icon = R.drawable.settings
    )
}