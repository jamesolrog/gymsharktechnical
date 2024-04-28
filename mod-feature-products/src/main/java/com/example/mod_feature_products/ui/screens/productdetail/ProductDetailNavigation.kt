package com.example.mod_feature_products.ui.screens.productdetail

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mod_feature_products.ui.screens.productlist.ProductListRoutePattern

private const val productIdArg = "productId"
const val ProductDetailRoutePattern = "$ProductListRoutePattern/{$productIdArg}"

fun NavGraphBuilder.productDetailScreen(
    onBackButtonPress: () -> Unit,
) {
    composable(
        route = ProductDetailRoutePattern,
    ) {
        val viewModel: ProductDetailViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState() // TODO LIFECYCLE
        ProductDetailScreen(
            uiState = uiState,
            onBackButtonPress = onBackButtonPress,
        )
    }
}

fun NavController.navigateToProductDetail(productId: Long) {
    navigate(route = "$ProductListRoutePattern/$productId") {
        launchSingleTop = true
    }
}

internal class ProductDetailArgs(val productId: String) {
    constructor(
        savedStateHandle: SavedStateHandle
    ) : this (
        productId = checkNotNull<String>(savedStateHandle[productIdArg])
    )
}
