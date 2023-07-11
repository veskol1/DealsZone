package com.vesko.amazondeals.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazondeals.R

@Composable
fun ErrorScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Something went wrong", fontSize = 32.sp, textAlign = TextAlign.Center, lineHeight = 40.sp)
        Text("Try again later..", fontSize = 32.sp, textAlign = TextAlign.Center, lineHeight = 40.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Image(painter = painterResource(id = R.drawable.sad_icon), contentDescription = "sad smile",Modifier.size(100.dp))
    }
}


@Composable
@Preview (backgroundColor = 0xFFFFFFFF, showBackground = true)
fun previewErrorScreen () {
    ErrorScreen()
}