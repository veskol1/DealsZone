package com.vesko.deals_zone.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.round

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }


@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

fun getPercentage(price: String, realPrice: String): Int {
    return round(1.minus(price.toFloat().div(realPrice.toFloat())) * 100).toInt()
}

fun getStringPercentage(price: String, realPrice: String): String {
    return "-${round(1.minus(price.toFloat().div(realPrice.toFloat())) * 100).toInt()}%"
}