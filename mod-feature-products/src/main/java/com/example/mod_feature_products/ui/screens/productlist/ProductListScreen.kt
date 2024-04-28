package com.example.mod_feature_products.ui.screens.productlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.mod_data_products.models.ProductLabel
import com.example.mod_feature_products.ui.components.ProductCard
import com.example.mod_ui_core.theme.GymsharkTechnicalTheme

@Composable
fun ProductListScreen(
    uiState: ProductListUiState,
    onProductClick: (productId: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ){
        when(uiState) {
            is ProductListUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is ProductListUiState.Ready -> ProductList(productSummaries = uiState.products, onProductClick)
            is ProductListUiState.Error -> Text(uiState.message, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun ProductList(
    productSummaries: List<ProductSummary>,
    onProductClick: (productId: Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        productSummaries.forEach {
            item {
                ProductCard(
                    featuredImageUrl = it.featuredMediaUrl,
                    title = it.title,
                    colour = it.colour,
                    inStock = it.inStock,
                    label = it.primaryLabel,
                    price = it.price,
                    onClick = { onProductClick(it.id) }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    GymsharkTechnicalTheme {
        ProductListScreen(
            uiState = ProductListUiState.Ready(
                products = listOf(
                    ProductSummary(
                        id = 1L,
                        sku = "SKU-1",
                        inStock = true,
                        title = "Product 1",
                        primaryLabel = ProductLabel.NEW,
                        colour = "Black",
                        price = "£100",
                        featuredMediaUrl = "",
                    ),
                    ProductSummary(
                        id = 2L,
                        sku = "SKU-1",
                        inStock = true,
                        title = "Product 2",
                        primaryLabel = ProductLabel.NEW,
                        colour = "Red",
                        price = "£40",
                        featuredMediaUrl = "",
                    )
                )
            ),
            onProductClick = {}
        )
    }
}