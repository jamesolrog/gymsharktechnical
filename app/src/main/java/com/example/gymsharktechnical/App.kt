package com.example.gymsharktechnical

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mod_feature_products.ui.screens.ProductGraphRoutePattern
import com.example.mod_ui_core.theme.GymsharkTechnicalTheme

@Composable
fun App(
    modifier: Modifier = Modifier,
) {
    val appState = rememberAppState()

    val navHost = remember {
        movableContentOf<PaddingValues> { innerPadding ->
            NavHost(
                navController = appState.navHostController,
                startDestination = ProductGraphRoutePattern,
                modifier = Modifier.padding(innerPadding)
            ) {
                appNavigationGraph(appState)
            }
        }
    }

    val currentBackStackEntry = appState.navHostController.currentBackStackEntryAsState()

    GymsharkTechnicalTheme {
        Scaffold(
            modifier = modifier,
        ) { innerPadding ->
            navHost(innerPadding)
        }
    }
}