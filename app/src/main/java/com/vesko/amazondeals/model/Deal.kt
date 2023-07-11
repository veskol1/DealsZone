package com.vesko.amazondeals.model

data class Deals(
    val list: ArrayList<Deal>
)

data class Deal(
    val id: String,
    val title: String,
    val price: String,
    val realPrice: String,
    val category: String,
    val link: String,
    val description: String,
    val imageDeal: String
)