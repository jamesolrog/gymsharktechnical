package com.example.mod_feature_products.ui.screens

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.mod_feature_products.ui.screens.productdetail.navigateToProductDetail
import com.example.mod_feature_products.ui.screens.productdetail.productDetailScreen
import com.example.mod_feature_products.ui.screens.productlist.ProductListRoutePattern
import com.example.mod_feature_products.ui.screens.productlist.productListScreen

const val ProductGraphRoutePattern = "productsGraph"

fun NavGraphBuilder.productsGraph(navController: NavController) {
    navigation(
        route = ProductGraphRoutePattern,
        startDestination = ProductListRoutePattern,
    ) {
        productListScreen(
            onProductClick = { productId ->
                navController.navigateToProductDetail(productId)
            }
        )
        productDetailScreen(
            onBackButtonPress = {
                navController.popBackStack()
            }
        )
    }
}