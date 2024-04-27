package com.example.mod_feature_products.ui.screens.productlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mod_data_products.Product
import com.example.mod_feature_products.ui.components.ProductCard

@Composable
fun ProductListScreen(
    uiState: ProductListUiState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ){
        when(uiState) {
            is ProductListUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is ProductListUiState.Ready -> ProductList(products = uiState.products)
            is ProductListUiState.Error -> Text(uiState.message, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun ProductList(products: List<Product>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        products.forEach {
            item {
                ProductCard(
                    featuredImageUrl = it.featuredMedia.src,
                    title = it.title,
                    colour = it.colour,
                    inStock = it.inStock,
                    label = it.labels?.firstOrNull(),
                    price = it.price,
                )
            }
        }
    }
}