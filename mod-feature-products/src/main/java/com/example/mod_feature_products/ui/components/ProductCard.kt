package com.example.mod_feature_products.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mod_data_products.models.Product
import com.example.mod_data_products.models.ProductLabel
import com.example.mod_data_products.models.SampleProducts
import com.example.mod_ui_core.theme.GymsharkTechnicalTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductCard(
    featuredImageUrl: String,
    title: String,
    colour: String,
    inStock: Boolean,
    price: String,
    label: ProductLabel?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
    ) {
        Box {
            Column(modifier = Modifier.semantics(mergeDescendants = true) {}) {
                Box(modifier = Modifier.height(225.dp)) {
                    AsyncImage(
                        model = featuredImageUrl,
                        contentScale = ContentScale.Crop,
                        error = rememberVectorPainter(image = Icons.Outlined.Warning),
                        contentDescription = null, // TODO - Accessibility
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                    label?.let {
                        ProductLabel(
                            label = it,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(start = 8.dp, bottom = 8.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .semantics(mergeDescendants = true) {}
                ) {
                    Text(title, maxLines = 1, modifier = Modifier.basicMarquee(delayMillis = 2500))
                    Text(
                        colour,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        price,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            if (!inStock) {
                Box(
                    modifier = Modifier
                        .background(Color.Gray.copy(alpha = 0.4f))
                        .fillMaxSize()
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview(
    @PreviewParameter(ProductPreviewParameterProvider::class) product: Product,
) {
    GymsharkTechnicalTheme {
        ProductCard(
            featuredImageUrl = product.featuredMedia.src,
            title = product.title,
            colour = product.colour,
            inStock = product.inStock,
            label = product.labels?.firstOrNull(),
            price = product.price.toString(),
            onClick = {},
        )
    }
}

class ProductPreviewParameterProvider : PreviewParameterProvider<Product> {
    override val values = SampleProducts.values
}