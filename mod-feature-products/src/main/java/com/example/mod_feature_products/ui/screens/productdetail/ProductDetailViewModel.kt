package com.example.mod_feature_products.ui.screens.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mod_data_products.GetProductUseCase
import com.example.mod_data_products.models.ProductLabel
import com.example.mod_utils_currency.CurrencyFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductDetailInfo(
    val id: Long,
    val sku: String,
    val inStock: Boolean,
    val title: String,
    val primaryLabel: ProductLabel?,
    val colour: String,
    val price: String,
    val featuredMediaUrl: String,
)

sealed class ProductDetailUiState {
    data object Loading: ProductDetailUiState()

    data class Ready(
        val productInfo: ProductDetailInfo,
    ): ProductDetailUiState()

    data class Error(
        val message: String,
    ): ProductDetailUiState()
}

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    getProductUseCase: GetProductUseCase,
    currencyFormatter: CurrencyFormatter,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val productDetailArgs: ProductDetailArgs = ProductDetailArgs(savedStateHandle)

    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val product = getProductUseCase.getProduct(id = productDetailArgs.productId)
                val productInfo = ProductDetailInfo(
                        id = product.id,
                        sku = product.sku,
                        inStock = product.inStock,
                        title = product.title,
                        primaryLabel = product.labels?.firstOrNull(),
                        colour = product.colour,
                        price = currencyFormatter.format(product.price, maximumFractionDigits = 0),
                        featuredMediaUrl = product.featuredMedia.src,
                    )
                _uiState.update {
                    ProductDetailUiState.Ready(productInfo)
                }
            } catch (e: Exception) {
                _uiState.update {
                    ProductDetailUiState.Error(message = e.localizedMessage ?: "ERROR")
                }
            }
        }
    }
}