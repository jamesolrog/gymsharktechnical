package com.example.mod_data_products

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

data class ProductsApiResponse(
    val hits: List<Product>
)

enum class Size {
    @SerializedName("xs")
    XS,
    @SerializedName("s")
    S,
    @SerializedName("m")
    M,
    @SerializedName("l")
    L,
    @SerializedName("xl")
    XL,
    @SerializedName("xxl")
    XXL,
}


enum class ProductLabel {
    @SerializedName("new") NEW,
    @SerializedName("going-fast") GOING_FAST,
    @SerializedName("popular") POPULAR,
    @SerializedName("limited-edition") LIMITED_EDITION,
}

enum class Gender {
    @SerializedName("f") FEMALE,
    @SerializedName("m") MALE,
}

data class Product(
    val id: Long,
    val sku: String,
    val inStock: Boolean,
    val sizeInStock: List<Size>,
    val availableSizes: List<AvailableSize>,
    val handle: String,
    val title: String,
    val description: String,
    val type: String,
    val gender: List<Gender>,
    val fit: String?,
    val labels: List<ProductLabel>?,
    val colour: String,
    val price: Int,
    val compareAtPrice: Int?,
    val discountPercentage: Int?,
    val featuredMedia: Media,
    val media: List<Media> = emptyList(),
    val objectID: String,
)

data class AvailableSize(
    val id: Long,
    val inStock: Boolean,
    val inventoryQuantity: Int,
    val price: Int,
    val size: Size,
    val sku: String,
)

data class Media(
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

interface ProductsApi {
    @GET("/training/mock-product-responses/algolia-example-payload.json")
    suspend fun getProducts(): ProductsApiResponse
}

class ProductsRemoteDataSource @Inject constructor(retrofit: Retrofit) {

    private val api by lazy { retrofit.create(ProductsApi::class.java) }

    suspend fun getProducts() = api.getProducts()
}