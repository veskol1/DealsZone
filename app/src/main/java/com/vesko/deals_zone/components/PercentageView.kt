package com.vesko.deals_zone.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vesko.deals_zone.R

@Composable
fun PercentageView(color: Color = colorResource(id = R.color.red), percentagePrice: String, margin: Dp = 0.dp, fontSize: TextUnit = 14.sp) {
    Box(modifier = Modifier
        .padding(margin)
        .clip(shape = RoundedCornerShape(6.dp))
        .padding(0.dp)
        .background(color = color)
    ) {
        Text(
            text = percentagePrice,
            modifier = Modifier.padding(8.dp),
            color = colorResource(id = R.color.white),
            fontSize = fontSize
        )
    }
}