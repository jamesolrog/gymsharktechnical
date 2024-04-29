package com.example.mod_data_products

import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource,
) {
    suspend fun getProducts() = productsRemoteDataSource.getProducts().hits

    // TODO: Consider optimising using local cache?
    suspend fun  getProduct(id: String) = getProducts().first { it.id.toString() == id }
}