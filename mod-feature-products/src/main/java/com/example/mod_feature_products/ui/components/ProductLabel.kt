package com.example.mod_feature_products.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mod_data_products.models.ProductLabel
import com.example.mod_feature_products.R

@Composable
fun ProductLabel(label: ProductLabel, modifier: Modifier = Modifier) {
    Text(
        text = getProductLabelName(label),
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.75f))
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