@file:OptIn(ExperimentalCoroutinesApi::class)

package com.online.shoppinglist.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.online.shoppinglist.data.model.Product
import com.online.shoppinglist.data.model.Rating
import com.online.shoppinglist.data.network.ServiceEndPoints
import com.online.shoppinglist.utils.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.*
import org.junit.Assert.*

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class ProductRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var serviceEndPoints: ServiceEndPoints

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

    @Test
    fun `success with empty product list`() = runTest {
        Mockito.`when`(serviceEndPoints.getProductList())
            .thenReturn(Response.success(emptyList()))
        val repository = ProductRepositoryImpl(serviceEndPoints)
        val result = repository.getProductList()
        assertEquals(Status.SUCCESS, result.status)
        assertNotNull(result.data)
        assertTrue(result.data!!.isEmpty())
    }

    @Test
    fun `success with valid product list`() = runTest {
        Mockito.`when`(serviceEndPoints.getProductList())
            .thenReturn(Response.success(productList))
        val repository = ProductRepositoryImpl(serviceEndPoints)
        val result = repository.getProductList()
        assertEquals(Status.SUCCESS, result.status)
        assertNotNull(result.data)
        assertEquals(productList, result.data)
    }

    @Test
    fun `success with null response`() = runTest {
        Mockito.`when`(serviceEndPoints.getProductList())
            .thenReturn(Response.success(null))
        val repository = ProductRepositoryImpl(serviceEndPoints)
        val result = repository.getProductList()
        assertEquals(Status.ERROR, result.status)
        assertNull(result.data)
        assertEquals("Unknown error", result.message)
    }

    @Test
    fun `error with Network exception response`() = runTest {
        Mockito.`when`(serviceEndPoints.getProductList())
            .thenReturn(Response.error(404, "Unknown Host Exception".toResponseBody()))
        val repository = ProductRepositoryImpl(serviceEndPoints)
        val result = repository.getProductList()
        assertEquals(Status.ERROR, result.status)
        assertNull(result.data)
        assertEquals("Unknown Host Exception", result.message)
    }

    @Test
    fun `error with empty invalid request `() = runTest {
        Mockito.`when`(serviceEndPoints.getProductList())
            .thenReturn(Response.error(500, "Bad request".toResponseBody()))
        val repository = ProductRepositoryImpl(serviceEndPoints)
        val result = repository.getProductList()
        assertEquals(Status.ERROR, result.status)
        assertEquals("Bad request", result.message)
    }

    // Product details tests


    @Test
    fun `success with valid product details`() = runTest {
        Mockito.`when`(serviceEndPoints.getProductDetails(productId.toString()))
            .thenReturn(Response.success(productList.get(1)))
        val repository = ProductRepositoryImpl(serviceEndPoints)
        val result = repository.getProductDetails(productId.toString())
        assertEquals(Status.SUCCESS, result.status)
        assertNotNull(result.data)
        assertEquals(productList.get(1), result.data)
    }

    @Test
    fun `success with null product response`() = runTest {
        Mockito.`when`(serviceEndPoints.getProductDetails(productId.toString()))
            .thenReturn(Response.success(null))
        val repository = ProductRepositoryImpl(serviceEndPoints)
        val result = repository.getProductDetails(productId.toString())
        assertEquals(Status.ERROR, result.status)
        assertNull(result.data)
        assertEquals("Unknown error", result.message)
    }

    @Test
    fun `error with Network exception product response`() = runTest {
        Mockito.`when`(serviceEndPoints.getProductDetails(productId.toString()))
            .thenReturn(Response.error(404, "Unknown Host Exception".toResponseBody()))
        val repository = ProductRepositoryImpl(serviceEndPoints)
        val result = repository.getProductDetails(productId.toString())
        assertEquals(Status.ERROR, result.status)
        assertNull(result.data)
        assertEquals("Unknown Host Exception", result.message)
    }

    @Test
    fun `error with empty invalid product request `() = runTest {
        Mockito.`when`(serviceEndPoints.getProductDetails(productId.toString()))
            .thenReturn(Response.error(500, "Bad request".toResponseBody()))
        val repository = ProductRepositoryImpl(serviceEndPoints)
        val result = repository.getProductDetails(productId.toString())
        assertEquals(Status.ERROR, result.status)
        assertEquals("Bad request", result.message)
    }


}