package com.example.mod_data_products

import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend fun getProduct(id: String) = productsRepository.getProduct(id)
}