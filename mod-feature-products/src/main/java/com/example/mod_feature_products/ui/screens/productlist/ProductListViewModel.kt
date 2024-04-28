package com.example.mod_feature_products.ui.screens.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mod_data_products.GetProductsUseCase
import com.example.mod_data_products.models.ProductLabel
import com.example.mod_utils_currency.CurrencyFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductSummary(
    val id: Long,
    val sku: String,
    val inStock: Boolean,
    val title: String,
    val primaryLabel: ProductLabel?,
    val colour: String,
    val price: String,
    val featuredMediaUrl: String,
)

sealed class ProductListUiState {
    data object Loading: ProductListUiState()

    data class Ready(
        val products: List<ProductSummary>,
    ): ProductListUiState()

    data class Error(
        val message: String,
    ): ProductListUiState()
}

@HiltViewModel
class ProductListViewModel @Inject constructor(
    getProductsUseCase: GetProductsUseCase,
    currencyFormatter: CurrencyFormatter,
): ViewModel() {
    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val products = getProductsUseCase.getProducts()
                val summarisedProducts = products.map {
                    ProductSummary(
                        id = it.id,
                        sku = it.sku,
                        inStock = it.inStock,
                        title = it.title,
                        primaryLabel = it.labels?.firstOrNull(),
                        colour = it.colour,
                        price = currencyFormatter.format(it.price),
                        featuredMediaUrl = it.featuredMedia.src,
                    )
                }
                _uiState.update {
                    ProductListUiState.Ready(summarisedProducts)
                }
            } catch (e: Exception) {
                _uiState.update {
                    ProductListUiState.Error(message = e.localizedMessage ?: "ERROR")
                }
            }
        }
    }
}