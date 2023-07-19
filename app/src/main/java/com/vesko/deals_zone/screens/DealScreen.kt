package com.vesko.deals_zone.screens


import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vesko.deals_zone.components.OutlinedBuyButton
import com.vesko.deals_zone.components.TopBar
import com.vesko.deals_zone.model.Deal


@Composable
fun DealScreen(deal: Deal, onBackClicked: () -> Unit) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBar(title = deal.title.take(20), onBackClicked = onBackClicked) }
    ) {
        BackHandler { // handle back pressed
            onBackClicked()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.height(300.dp),
                model = deal.imageDeal,
                contentDescription = null,
            )

            Divider(modifier = Modifier.padding(top = 8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "About this item:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Row(modifier = Modifier
                    .wrapContentHeight()
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    )
                    .clickable { expanded = !expanded }
                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                    .then(
                        if (expanded) {
                            Modifier
                        } else {
                            Modifier
                                .height(200.dp)
                                .drawWithContent {
                                    val colors = listOf(Color.Black, Color.Transparent)
                                    drawContent()
                                    drawRect(
                                        brush = Brush.verticalGradient(colors),
                                        blendMode = BlendMode.DstIn
                                    )
                                }
                        }
                    )
                ) {
                    Text(text = deal.description)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row( horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Price: $" + deal.price, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text(text = "$"+deal.realPrice, fontWeight = FontWeight.Bold, fontSize = 16.sp, style = TextStyle(textDecoration = TextDecoration.LineThrough))

                    }
                  Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                        OutlinedBuyButton(context = context, link = deal.link)
                    }
                }
            }
        }
    }
}