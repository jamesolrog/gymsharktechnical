package com.example.mod_feature_products.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mod_data_products.Gender
import com.example.mod_data_products.Media
import com.example.mod_data_products.Product
import com.example.mod_data_products.ProductLabel
import com.example.mod_feature_products.R
import com.example.mod_ui_core.theme.GymsharkTechnicalTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductCard(
    featuredImageUrl: String,
    title: String,
    colour: String,
    inStock: Boolean,
    label: ProductLabel?,
    price: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = { /*TODO*/ },
        modifier = modifier,
    ) {
        Box {
            Column {
                Box(modifier = Modifier.height(225.dp)) {
                    AsyncImage(
                        model = featuredImageUrl,
                        contentScale = ContentScale.Crop,
                        contentDescription = null, // TODO - Accessibility
                    )
                    label?.let {
                        ProductLabel(label = it, modifier = Modifier.align(Alignment.BottomStart))
                    }
                }

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(title, maxLines = 1, modifier = Modifier.basicMarquee(delayMillis = 2500))
                    Text(colour, style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text((price/100).toString(), style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            if (!inStock) {
                Box(modifier = Modifier
                    .background(Color.Gray.copy(alpha = 0.4f))
                    .fillMaxSize()
                )
            }
        }

    }
}

@Composable
private fun ProductLabel(label: ProductLabel, modifier: Modifier = Modifier) {
    Text(
        text = getProductLabelName(label),
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
        modifier = modifier
            .padding(start = 8.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.75f))
            .padding(4.dp)
    )
}

@Composable
private fun getProductLabelName(label: ProductLabel): String {
    return when(label) {
        ProductLabel.NEW -> stringResource(id = R.string.new_label)
        ProductLabel.POPULAR -> stringResource(id = R.string.popular_label)
        ProductLabel.GOING_FAST -> stringResource(id = R.string.going_fast_label)
        ProductLabel.LIMITED_EDITION -> stringResource(id = R.string.limited_edition_label)
    }
}

@PreviewLightDark
@Composable
private fun Preview(
    @PreviewParameter(ProductPreviewParameterProvider::class) product: Product
) {
    GymsharkTechnicalTheme {
        ProductCard(
            featuredImageUrl = product.featuredMedia.src,
            title = product.title,
            colour = product.colour,
            inStock = product.inStock,
            label = product.labels?.firstOrNull(),
            price = product.price,
        )
    }
}

class ProductPreviewParameterProvider : PreviewParameterProvider<Product> {
    override val values = sequenceOf(
        Product(
            id = 1L,
            sku = "SKU-1",
            inStock = true,
            sizeInStock = emptyList(),
            availableSizes = emptyList(),
            handle = "",
            title = "Product 1",
            description = "",
            type = "",
            gender = listOf(Gender.FEMALE),
            fit = "",
            labels = listOf(ProductLabel.NEW),
            colour = "Black",
            price = 1000,
            compareAtPrice = null,
            discountPercentage = null,
            featuredMedia = Media("", null, "", 1, 1L, 1, 1L, "https://cdn.shopify.com/s/files/1/1326/4923/products/SpeedLeggingsMoonstoneBlue-B3A3E-UBFC.D11_ZH_ZH.jpg?v=1649148932", "", emptyList(), 1),
            media = emptyList(),
            objectID = "1",
        ),
        Product(
            id = 2L,
            sku = "SKU-2",
            inStock = false,
            sizeInStock = emptyList(),
            availableSizes = emptyList(),
            handle = "",
            title = "Product 2",
            description = "",
            type = "",
            gender = listOf(Gender.MALE),
            fit = "",
            labels = null,
            colour = "Blue",
            price = 1000,
            compareAtPrice = null,
            discountPercentage = null,
            featuredMedia = Media("", null, "", 1, 1L, 1, 1L, "https://cdn.shopify.com/s/files/1/1326/4923/products/SpeedLeggingsMoonstoneBlue-B3A3E-UBFC.D3_ZH_ZH.jpg?v=1649148932", "", emptyList(), 1),
            media = emptyList(),
            objectID = "1",
        ),
    )
}