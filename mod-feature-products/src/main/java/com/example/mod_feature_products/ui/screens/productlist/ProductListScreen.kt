package com.example.mod_feature_products.ui.screens.productlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mod_data_products.Product
import com.example.mod_ui_core.ProductCard

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
    LazyColumn {
        products.forEach {
            item {
                ProductCard(title = it.id.toString())
            }
        }
    }
}