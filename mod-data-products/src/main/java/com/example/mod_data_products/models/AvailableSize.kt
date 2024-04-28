package com.example.mod_data_products.models

data class AvailableSize(
    val id: Long,
    val inStock: Boolean,
    val inventoryQuantity: Int,
    val price: Int,
    val size: Size,
    val sku: String,
)