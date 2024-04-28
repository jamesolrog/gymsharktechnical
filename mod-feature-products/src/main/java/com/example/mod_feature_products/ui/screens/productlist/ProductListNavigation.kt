package com.example.mod_feature_products.ui.screens.productlist

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation

const val ProductListRoutePattern = "products"

fun NavGraphBuilder.productListScreen(
    onProductClick: (productId: Long) -> Unit,
) {
    composable(
        route = ProductListRoutePattern,
    ) {
        val viewModel: ProductListViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState() // TODO LIFECYCLE
        ProductListScreen(uiState = uiState, onProductClick)
    }
}

fun NavController.navigateToProductList(builder: NavOptionsBuilder.() -> Unit) {
    navigate(
        route = ProductListRoutePattern,
        navOptions = navOptions(builder),
    )
}