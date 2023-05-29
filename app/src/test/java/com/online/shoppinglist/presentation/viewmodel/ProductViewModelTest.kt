package com.online.shoppinglist.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.online.shoppinglist.data.model.Product
import com.online.shoppinglist.data.model.Rating
import com.online.shoppinglist.data.repository.ProductRepositoryImpl
import com.online.shoppinglist.utils.ServiceResponse
import com.online.shoppinglist.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var repositoryImpl: ProductRepositoryImpl

    private val productList = listOf(
        Product(
            id = 1,
            title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            price = 109.95,
            description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            rating = Rating()
        ),
        Product(
            id = 2,
            title = "Mens Casual Premium Slim Fit T-Shirts",
            price = 22.3,
            description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            rating = Rating()
        ),
        Product(
            id = 3,
            title = "Mens Cotton Jacket",
            price = 55.99,
            description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm-hearted love to Father, husband or son on this Thanksgiving or Christmas Day.",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
            rating = Rating()
        ),
        Product(
            id = 4,
            title = "Mens Casual Slim Fit",
            price = 15.99,
            description = "The color could be slightly different between on the screen and in practice. / Please note that body builds vary by person, therefore, detailed size information should be reviewed below on the product description.",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg",
            rating = Rating()

        )
    )

    private val productId = 2

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    // Tests for product list
    @Test
    fun `get successful product list`() = runTest {
        Mockito.`when`(repositoryImpl.getProductList())
            .thenReturn(ServiceResponse.success(productList))
        val viewModel = ProductViewModel(repositoryImpl)
        viewModel.getProductList()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.products.getOrAwaitValue()
        assertNotNull(result.data)
        assertEquals(productList.get(1), result.data!!.get(1))
    }

    @Test
    fun `get successful product empty list`() = runTest {
        Mockito.`when`(repositoryImpl.getProductList())
            .thenReturn(ServiceResponse.success(emptyList()))
        val viewModel = ProductViewModel(repositoryImpl)
        viewModel.getProductList()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.products.getOrAwaitValue()
        assertNotNull(result.data)
        assertEquals(0, result.data!!.size)
    }

    @Test
    fun `get error product empty list`() = runTest {
        Mockito.`when`(repositoryImpl.getProductList())
            .thenReturn(ServiceResponse.error("Unknown Error", emptyList()))
        val viewModel = ProductViewModel(repositoryImpl)
        viewModel.getProductList()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.products.getOrAwaitValue()
        assertNotNull(result)
        assertEquals("Unknown Error", result.message)
    }
    // Test for product details

    @Test
    fun `get successful product details`() = runTest {
        Mockito.`when`(repositoryImpl.getProductDetails(productId.toString()))
            .thenReturn(ServiceResponse.success(getProduct()))
        val viewModel = ProductViewModel(repositoryImpl)
        viewModel.getProductDetails(productId.toString())
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.product.getOrAwaitValue()
        assertNotNull(result.data)
        assertEquals(productId, result.data!!.id)
    }

    @Test
    fun `get successful product empty result`() = runTest {
        Mockito.`when`(repositoryImpl.getProductDetails(productId.toString()))
            .thenReturn(ServiceResponse.success(null))
        val viewModel = ProductViewModel(repositoryImpl)
        viewModel.getProductDetails(productId.toString())
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.product.getOrAwaitValue()
        assertNotNull(result)
        assertEquals(null, result.data)
    }

    @Test
    fun `get error product empty product details`() = runTest {
        Mockito.`when`(repositoryImpl.getProductDetails(productId.toString()))
            .thenReturn(ServiceResponse.error("Unknown Error", null))
        val viewModel = ProductViewModel(repositoryImpl)
        viewModel.getProductDetails(productId.toString())
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.product.getOrAwaitValue()
        assertNotNull(result)
        assertEquals("Unknown Error", result.message)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun getProduct() : Product{
        return Product(
            id = 2,
            title = "Mens Casual Premium Slim Fit T-Shirts",
            price = 22.3,
            description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            rating = Rating()
        )
    }
}