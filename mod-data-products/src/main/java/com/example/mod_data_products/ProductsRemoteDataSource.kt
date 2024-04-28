package com.example.mod_data_products

import com.example.mod_data_products.models.Product
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

data class ProductsApiResponse(val hits: List<Product>)

interface ProductsApi {
    @GET("/training/mock-product-responses/algolia-example-payload.json")
    suspend fun getProducts(): ProductsApiResponse
}

class ProductsRemoteDataSource @Inject constructor(retrofit: Retrofit) {
    private val api by lazy { retrofit.create(ProductsApi::class.java) }
    suspend fun getProducts() = api.getProducts()
}