package com.example.mod_ui_core

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mod_ui_core.theme.GymsharkTechnicalTheme

@Composable
fun ProductCard(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(title)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    GymsharkTechnicalTheme() {
        ProductCard("Test")
    }
}