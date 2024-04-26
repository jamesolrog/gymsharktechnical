package com.example.mod_feature_products.ui.screens.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mod_data_products.GetProductsUseCase
import com.example.mod_data_products.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProductListUiState {
    data object Loading: ProductListUiState()

    data class Ready(
        val products: List<Product>,
    ): ProductListUiState()

    data class Error(
        val message: String,
    ): ProductListUiState()
}

@HiltViewModel
class ProductListViewModel @Inject constructor(
    getProductsUseCase: GetProductsUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val products = getProductsUseCase.getProducts()
                _uiState.update {
                    ProductListUiState.Ready(products)
                }
            } catch (e: Exception) {
                _uiState.update {
                    ProductListUiState.Error(message = e.localizedMessage ?: "ERROR")
                }
            }
        }
    }
}