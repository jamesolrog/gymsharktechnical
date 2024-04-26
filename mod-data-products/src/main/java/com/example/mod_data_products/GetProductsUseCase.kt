package com.example.mod_data_products

import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend fun getProducts() = productsRepository.getProducts()
}