package com.example.mod_feature_products.ui.screens.productdetail

import androidx.lifecycle.SavedStateHandle
import com.example.mod_data_products.GetProductUseCase
import com.example.mod_data_products.models.SampleProducts
import com.example.mod_utils_currency.CurrencyFormatter
import com.example.mod_utils_test.MainDispatcherRule
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.stub
import org.mockito.kotlin.verifyBlocking

private const val FAKE_PRODUCT_ID = "FAKE_PRODUCT_ID"
private const val FAKE_PRICE = "£10"

class ProductDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockGetProductUseCase = mock<GetProductUseCase>()
    private val mockCurrencyFormatter = mock<CurrencyFormatter>()
    private val mockSavedStateHandle = mock<SavedStateHandle>()

    private val viewModel by lazy {
        ProductDetailViewModel(mockGetProductUseCase, mockCurrencyFormatter, mockSavedStateHandle)
    }

    @Before
    fun setUp() {
        mockSavedStateHandle.stub {
            on { get<String>(any()) } doReturn FAKE_PRODUCT_ID
        }
        mockCurrencyFormatter.stub {
            on { format(eq(1000), any(), any()) } doReturn FAKE_PRICE
        }
    }

    @Test
    fun `Given a new viewModel, Then the initial ui state is loading`() = runTest {
        mockGetProductUseCase.stub {
            onBlocking { getProduct(any()) } doSuspendableAnswer {
                // Delay the call to get products to ensure loading state has time to assert
                awaitCancellation()
            }
        }
        assert(viewModel.uiState.value == ProductDetailUiState.Loading)
    }

    @Test
    fun `Given a new viewModel, When the viewModel inits, Then a call to get a single product is made`() =
        runTest {
            viewModel
            verifyBlocking(mockGetProductUseCase) {
                getProduct(FAKE_PRODUCT_ID)
            }
        }

    @Test
    fun `Given the call to a product is successful, When the call is complete, Then the ui state is updated to contain product information`() =
        runTest {
            val fakeProduct = SampleProducts.values.first()
            val expectedUiState = ProductDetailUiState.Ready(
                productInfo = ProductDetailInfo(
                    id = fakeProduct.id,
                    sku = fakeProduct.sku,
                    inStock = fakeProduct.inStock,
                    title = fakeProduct.title,
                    primaryLabel = fakeProduct.labels?.firstOrNull(),
                    colour = fakeProduct.colour,
                    price = FAKE_PRICE,
                    featuredMediaUrl = fakeProduct.featuredMedia.src,
                )
            )
            mockGetProductUseCase.stub {
                onBlocking { getProduct(FAKE_PRODUCT_ID) } doReturn fakeProduct
            }

            assert(viewModel.uiState.value == expectedUiState)
        }

    @Test
    fun `Given the call to get all products fails, When call is complete, Then the ui state is updated to contain an error message`() =
        runTest {
            val errorMessage = "There was an error."
            mockGetProductUseCase.stub {
                onBlocking { getProduct(FAKE_PRODUCT_ID) } doAnswer { throw Exception(errorMessage) }
            }

            assert(viewModel.uiState.value == ProductDetailUiState.Error(errorMessage))
        }
}