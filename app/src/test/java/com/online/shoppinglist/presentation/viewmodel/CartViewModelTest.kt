package com.online.shoppinglist.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.online.shoppinglist.data.model.Product
import com.online.shoppinglist.data.model.Rating
import com.online.shoppinglist.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CartViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `check product successfully adding to cart`(){
        val viewModel = CartViewModel()
        viewModel.addItemsToCart(getProduct())
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.carts.getOrAwaitValue()
        assertNotNull(result)
        assertEquals(getProduct(), result.get(0))

    }
    @Test
    fun `check product successfully calculated from cart`(){
        val viewModel = CartViewModel()
        viewModel.setCartItemSum(productList)
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.getCartItemsCategoryGroups()
        assertNotNull(result)


    }
    private fun getProduct() : Product {
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
    private val productList = listOf(
        Product(
            id = 1,
            title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            price = 10.0,
            description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
            category = "A",
            image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            rating = Rating()
        ),
        Product(
            id = 2,
            title = "Mens Casual Premium Slim Fit T-Shirts",
            price = 20.0,
            description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
            category = "B",
            image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            rating = Rating()
        ),
        Product(
            id = 3,
            title = "Mens Cotton Jacket",
            price = 30.0,
            description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm-hearted love to Father, husband or son on this Thanksgiving or Christmas Day.",
            category = "A",
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
            rating = Rating()
        ),
        Product(
            id = 4,
            title = "Mens Casual Slim Fit",
            price = 40.0,
            description = "The color could be slightly different between on the screen and in practice. / Please note that body builds vary by person, therefore, detailed size information should be reviewed below on the product description.",
            category = "B",
            image = "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg",
            rating = Rating()

        )
        ,
        Product(
            id = 4,
            title = "Women Casual Slim Fit",
            price = 40.0,
            description = "The color could be slightly different between on the screen and in practice. / Please note that body builds vary by person, therefore, detailed size information should be reviewed below on the product description.",
            category = "B",
            image = "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg",
            rating = Rating()

        )
    )
}