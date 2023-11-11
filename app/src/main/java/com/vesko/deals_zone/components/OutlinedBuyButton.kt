package com.vesko.deals_zone.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.vesko.deals_zone.utils.mockDealsList

@Composable
fun OutlinedBuyButton(link: String) {
    val context = LocalContext.current
    OutlinedButton(onClick = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        context.startActivity(intent)
    }, contentPadding = ButtonDefaults.ButtonWithIconContentPadding ) {
        Icon(
            Icons.Filled.ShoppingCart,
            contentDescription = "Localized description",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = "Buy")
    }
}

@Preview
@Composable
fun OutlinedBuyButtonPreview() {
    OutlinedBuyButton(mockDealsList[0].link)
}