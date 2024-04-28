package com.example.mod_data_products.models

import com.google.gson.annotations.SerializedName

data class ProductMedia(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    val alt: String?,
    @SerializedName("created_at")
    val createdAt: String,
    val height: Int,
    val id: Long,
    val position: Int,
    @SerializedName("product_id")
    val productId: Long,
    val src: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("variant_ids")
    val variantIds: List<Long>,
    val width: Int
)

object SampleProductMedia {
    val values = sequenceOf(
        ProductMedia(
            "",
            "",
            "",
            1,
            1L,
            1,
            1L,
            "",
            "",
            emptyList(),
            1,
        )
    )
}