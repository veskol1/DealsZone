package com.vesko.deals_zone.components

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vesko.deals_zone.BuildConfig
import com.vesko.deals_zone.model.Deal

private const val ADD_POSITION = 6
@Composable
fun LazyColumnList(
    dealList: ArrayList<Deal>,
    dealFavoriteList: ArrayList<Deal>,
    navigateOnCardClick: (String) -> Unit,
    bottomBarVisible: (Boolean) -> Unit,
    snackbarHostState: SnackbarHostState,
    onClickFavoriteItem: (String) -> Unit,
    showWithoutBottomNavBar: Boolean = false,
    showAds: Boolean = false
) {
    LazyColumn(
        modifier = Modifier.padding(
            top = 0.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 0.dp.takeIf { showWithoutBottomNavBar } ?: 80.dp
        )
    ) {
        if (showAds) {
            itemsIndexed(dealList.take(30)) { index, deal ->
                if (index.mod(ADD_POSITION ) == 0 && index != 0) {
                    AdBanner(unitId = BuildConfig.BANNER_LIST)
                } else {
                    DealItem(
                        deal = deal,
                        favoriteDeal = dealFavoriteList.contains(deal),
                        snackbarHostState = snackbarHostState,
                        bottomBarVisible = bottomBarVisible,
                        navigateOnCardClick = { dealId ->
                            navigateOnCardClick(dealId)
                        },
                        onClickFavoriteItem = { dealId ->
                            onClickFavoriteItem(dealId)
                        },
                        searchViewItemList = false,
                    )
                }
            }
        } else {
            items(dealList.take(30)) { deal ->
                Log.d("haha","deal image "+deal.imageDealSmall)
                DealItem(
                    deal = deal,
                    favoriteDeal = dealFavoriteList.contains(deal),
                    snackbarHostState = snackbarHostState,
                    bottomBarVisible = bottomBarVisible,
                    navigateOnCardClick = { dealId ->
                        navigateOnCardClick(dealId)
                    },
                    onClickFavoriteItem = { dealId ->
                        onClickFavoriteItem(dealId)
                    },
                    searchViewItemList = false,
                )
            }

        }
    }
}
