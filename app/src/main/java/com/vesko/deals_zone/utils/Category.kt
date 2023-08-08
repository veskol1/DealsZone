package com.vesko.deals_zone.utils

import androidx.annotation.DrawableRes
import com.vesko.deals_zone.R

enum class Category(val value: String, @DrawableRes val icon: Int) {
    //Home("Home", R.drawable.home),
    Baby("Baby", R.drawable.stroller),
    Car("Car", R.drawable.car),
    //Diy("Diy", R.drawable.diy),
    Pets("Pets", R.drawable.pets),
    PC("PC", R.drawable.computer),
    Travel("Travel", R.drawable.travel),
    Beauty("Beauty", R.drawable.beauty),
    Electronics("Electronics", R.drawable.electronics),
    Fitness("Fitness", R.drawable.fitness),
}

val categoriesList = arrayListOf<Category>(
    Category.Baby,
    Category.Beauty,
    Category.Car,
    //Category.Diy,
    Category.Electronics,
    Category.Fitness,
   // Category.Home,
    Category.PC,
    Category.Pets,
    Category.Travel,

    )