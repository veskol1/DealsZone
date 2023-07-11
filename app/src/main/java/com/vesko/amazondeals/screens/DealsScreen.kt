package com.vesko.amazondeals.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.amazondeals.R
import com.vesko.amazondeals.components.OutlinedBuyButton
import com.vesko.amazondeals.components.SearchViewBar
import com.vesko.amazondeals.model.Deal
import com.vesko.amazondeals.viewmodel.DealsViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DealsScreen(
    navController: NavController,
    bottomBarVisible: (Boolean) -> Unit,
    dealsViewModel: DealsViewModel,
    snackbarHostState: SnackbarHostState
) {
    val dealUiState by dealsViewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchViewBar(dealsViewModel)
        when (dealUiState.status) {
            DealsViewModel.Status.LOADING -> {
                LoadingScreen()
            }
            DealsViewModel.Status.ERROR -> {
                ErrorScreen()
            }
            else -> {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(dealUiState.filteredList) { deal ->
                        DealItem(
                            deal = deal,
                            navController = navController,
                            snackbarHostState = snackbarHostState,
                            bottomBarVisible
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DealItem(
    deal: Deal,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    bottomBarVisible: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var saved by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .height(120.dp)
            .clickable {
                bottomBarVisible(false)
                navController.navigate("deal/${deal.id}")
            },
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
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp), horizontalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = deal.imageDeal,
                        contentDescription = null,
                    )
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
                    .fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .padding(4.dp), verticalArrangement = Arrangement.Center) {
                        Text(
                            text = "$" + deal.price,
                            color = colorResource(id = R.color.red),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(text = "$"+deal.realPrice, fontWeight = FontWeight.Bold, fontSize = 12.sp, style = TextStyle(textDecoration = TextDecoration.LineThrough))
                    }
                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .padding(2.dp), verticalArrangement = Arrangement.Bottom) {
                        IconButton(onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Saved to favorites".takeIf { saved } ?: "Removed From Favorites",
                                    duration = SnackbarDuration.Short
                                )
                            }
                            saved = !saved
                        }) {
                            Icon(imageVector = Icons.Default.Favorite.takeIf { saved }
                                ?: Icons.Default.FavoriteBorder,
                                tint = colorResource(id = R.color.red),
                                contentDescription = "heart icon")
                        }
                    }
                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .padding(2.dp), verticalArrangement = Arrangement.Bottom) {
                        OutlinedBuyButton(context = context, link = deal.link)
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DisplaySnackBar() {
    val snackbarHostState = remember {SnackbarHostState()}
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            ExtendedFloatingActionButton(
                onClick = {
                    // show snackbar as a suspend function
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Snackbar # ${++clickCount}"
                        )
                    }
                }
            ) { Text("Show snackbar") }
        },
        content = {}
    )

}

@Composable
@Preview
fun DealsScreenPreview() {
    //DealsScreen()
}