package com.example.mod_feature_products.ui.screens.productdetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mod_feature_products.ui.components.ProductLabel
import com.example.mod_ui_core.theme.GymsharkTechnicalTheme

@Composable
fun ProductDetailScreen(
    uiState: ProductDetailUiState,
    onBackButtonPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (uiState) {
            is ProductDetailUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

            is ProductDetailUiState.Ready -> ProductDetail(
                productInfo = uiState.productInfo,
                onBackButtonPress
            )

            is ProductDetailUiState.Error -> Text(
                uiState.message,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ProductDetail(productInfo: ProductDetailInfo, onBackButtonPress: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(productInfo.title, modifier = Modifier.basicMarquee())
                },
                navigationIcon = {
                    IconButton(onClick = onBackButtonPress) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .semantics(mergeDescendants = true) {}
        ) {
            Box {
                AsyncImage(
                    model = productInfo.featuredMediaUrl,
                    contentScale = ContentScale.Crop,
                    error = rememberVectorPainter(image = Icons.Outlined.Warning),
                    contentDescription = null, // TODO - Accessibility
                    modifier = Modifier
                        .heightIn(max = 500.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
                productInfo.primaryLabel?.let {
                    ProductLabel(
                        label = it,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 16.dp, bottom = 16.dp)
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .semantics(mergeDescendants = true) {}
            ) {
                Text(productInfo.title, maxLines = 2, style = MaterialTheme.typography.titleMedium)
                Text(productInfo.colour, style = MaterialTheme.typography.bodyLarge)
                Text(
                    productInfo.price,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    GymsharkTechnicalTheme {
        ProductDetailScreen(
            uiState = ProductDetailUiState.Ready(
                productInfo = ProductDetailInfo(
                    id = 1L,
                    sku = "SKU-1",
                    inStock = true,
                    title = "Product 1",
                    primaryLabel = com.example.mod_data_products.models.ProductLabel.NEW,
                    colour = "Black",
                    price = "£100",
                    featuredMediaUrl = "",
                ),
            ),
            onBackButtonPress = {},
        )
    }
}