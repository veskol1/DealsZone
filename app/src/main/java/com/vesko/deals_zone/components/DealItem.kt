package com.vesko.deals_zone.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vesko.deals_zone.R
import com.vesko.deals_zone.model.Deal
import com.vesko.deals_zone.utils.getPercentage
import com.vesko.deals_zone.utils.getStringPercentage
import com.vesko.deals_zone.utils.mockDealsList
import kotlinx.coroutines.launch

@Composable
fun DealItem(
    deal: Deal,
    favoriteDeal: Boolean,
    snackbarHostState: SnackbarHostState?,
    bottomBarVisible: (Boolean) -> Unit,
    navigateOnCardClick: (String) -> Unit,
    onClickFavoriteItem: (String) -> Unit,
    searchViewItemList: Boolean = false
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable {
                bottomBarVisible(false)
                navigateOnCardClick(deal.id)
            }
            .then(
                if (searchViewItemList) {
                    Modifier.padding(top = 16.dp)
                } else {
                    Modifier.padding(top = 8.dp, bottom = 8.dp)
                }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(0.dp)
                    .fillMaxHeight()
                    .background(color = Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clipToBounds(),contentAlignment = Alignment.TopEnd
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = deal.imageDealSmall,
                            contentDescription = null,
                        )
                    }
                    if (deal.hasCoupon) {
                        Box(modifier = Modifier.size(60.dp)) {
                            Image(painter = painterResource(id = R.drawable.coupon), contentDescription = null)
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(5f)
                    .padding(8.dp)
                    .fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text(
                        text = deal.title,
                        maxLines = 2,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )
                }
                Row(modifier = Modifier
                    .padding(bottom = 0.dp)
                    .fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight(), verticalArrangement = Arrangement.Center
                    ) {
                        val percentagePrice = getPercentage(price = deal.price, realPrice = deal.realPrice)
                        val percentageColor = when (percentagePrice) {
                            in 0..25 -> {
                                colorResource(id = R.color.red_light)
                            }
                            in 26..40 -> {
                                colorResource(id = R.color.red_normal)
                            }
                            else -> {
                                colorResource(id = R.color.red_strong)
                            }
                        }
                        PercentageView(
                            color = percentageColor,
                            percentagePrice = getStringPercentage(
                                price = deal.price,
                                realPrice = deal.realPrice
                            ),
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(4.dp), verticalArrangement = Arrangement.Center
                    ) {
                        Row {
                            Text(
                                text = "$${deal.price}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.red),
                            )
                        }

                        if (!searchViewItemList) {
                            Text(
                                text = "$${deal.realPrice}",
                                color = colorResource(id = R.color.grey),
                                fontSize = 12.sp,
                                style = TextStyle(textDecoration = TextDecoration.LineThrough)
                            )
                        }
                    }
                    if (!searchViewItemList) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(2.dp), verticalArrangement = Arrangement.Bottom
                        ) {
                            IconButton(onClick = {
                                scope.launch {
                                    snackbarHostState?.showSnackbar(
                                        message = "Removed From Favorites".takeIf { favoriteDeal }
                                            ?: "Saved to favorites",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                onClickFavoriteItem(deal.id)
                            }) {
                                Icon(imageVector = Icons.Default.Favorite.takeIf { favoriteDeal }
                                    ?: Icons.Default.FavoriteBorder,
                                    tint = colorResource(id = R.color.red),
                                    contentDescription = "heart icon")
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(2.dp), verticalArrangement = Arrangement.Bottom
                        ) {
                            BuyButton(context = context, link = deal.link)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun DealItemPreview() {
    DealItem(
        deal = mockDealsList[0],
        favoriteDeal = false ,
        navigateOnCardClick = {},
        bottomBarVisible = {},
        snackbarHostState = null,
        onClickFavoriteItem = {},
    )
}
