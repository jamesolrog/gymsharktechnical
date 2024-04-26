package com.example.gymsharktechnical

import androidx.navigation.NavGraphBuilder
import com.example.mod_feature_products.ui.screens.productlist.productsGraph

internal fun NavGraphBuilder.appNavigationGraph(appState: AppState) {
    productsGraph(appState.navHostController)
}