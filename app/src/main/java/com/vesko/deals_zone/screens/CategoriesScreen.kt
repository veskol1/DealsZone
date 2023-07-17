package com.vesko.deals_zone.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vesko.deals_zone.utils.Category
import com.vesko.deals_zone.utils.categoriesList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(bottomBarVisible: (Boolean) -> Unit, onCategorySelected: (Category) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Text(
                text = "Select Deals Category",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 12.dp),
                textAlign = TextAlign.Center
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 90.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalArrangement = Arrangement.SpaceBetween, content = {
                    items(categoriesList) { item ->
                        Card(
                            modifier = Modifier
                                .height(100.dp)
                                .padding(horizontal = 16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            onClick = {
                                onCategorySelected(item)
                                bottomBarVisible(false)
                            }
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceAround,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = item.value,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                                Icon(
                                    ImageVector.vectorResource(id = item.icon),
                                    contentDescription = "category icon",
                                    modifier = Modifier
                                        .padding(bottom = 16.dp)
                                        .size(34.dp)
                                )
                            }
                        }
                    }
                })
        }
    }
}