package com.example.mod_feature_products.ui.screens.productlist

import com.example.mod_data_products.GetProductsUseCase
import com.example.mod_data_products.models.SampleProducts
import com.example.mod_utils_currency.CurrencyFormatter
import com.example.mod_utils_test.MainDispatcherRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.stub
import org.mockito.kotlin.verifyBlocking

private const val FAKE_PRICE = "£10"

class ProductListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockGetProductsUseCase = mock<GetProductsUseCase>()
    private val mockCurrencyFormatter = mock<CurrencyFormatter>()

    private val viewModel by lazy {
        ProductListViewModel(mockGetProductsUseCase, mockCurrencyFormatter)
    }

    @Before
    fun setUp() {
        mockCurrencyFormatter.stub {
            on { format(any(), any()) } doReturn FAKE_PRICE
        }
    }

    @Test
    fun `Given a new viewModel, Then the initial ui state is loading`() = runTest {
        mockGetProductsUseCase.stub {
            onBlocking { getProducts() } doSuspendableAnswer {
                // Delay the call to get products to ensure loading state has time to assert
                delay(500)
                emptyList()
            }
        }
        assert(viewModel.uiState.value == ProductListUiState.Loading)
    }

    @Test
    fun `Given a new viewModel, When the viewModel inits, Then a call to get all products is made`() =
        runTest {
            viewModel
            verifyBlocking(mockGetProductsUseCase) {
                getProducts()
            }
        }

    @Test
    fun `Given the call to get all products is successful, When call is complete, Then the ui state is updated to contain product information`() =
        runTest {
            val productApiResult = SampleProducts.values.toList()
            val expectedUiState = ProductListUiState.Ready(
                products = productApiResult.map {
                    ProductSummary(
                        id = it.id,
                        sku = it.sku,
                        inStock = it.inStock,
                        title = it.title,
                        primaryLabel = it.labels?.firstOrNull(),
                        colour = it.colour,
                        price = FAKE_PRICE,
                        featuredMediaUrl = it.featuredMedia.src,
                    )
                }
            )
            mockGetProductsUseCase.stub {
                onBlocking { getProducts() } doReturn productApiResult
            }

            assert(viewModel.uiState.value == expectedUiState)
        }

    @Test
    fun `Given the call to get all products fails, When call is complete, Then the ui state is updated to contain an error message`() =
        runTest {
            val errorMessage = "There was an error."
            mockGetProductsUseCase.stub {
                onBlocking { getProducts() } doAnswer { throw Exception(errorMessage) }
            }

            assert(viewModel.uiState.value == ProductListUiState.Error(errorMessage))
        }
}