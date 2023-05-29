package com.online.shoppinglist.presentation.ui

import android.view.View
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.online.shoppinglist.R
import com.online.shoppinglist.data.model.Product
import com.online.shoppinglist.data.model.Rating
import com.online.shoppinglist.domain.repository.ProductRepository
import com.online.shoppinglist.launchFragmentInHiltContainer
import com.online.shoppinglist.presentation.adapter.ProductListAdapter
import com.online.shoppinglist.presentation.viewmodel.ProductViewModel
import com.online.shoppinglist.utils.ServiceResponse
import com.online.shoppinglist.utils.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class ProductListFragmentTest() {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)



    @Before
    fun setUp() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `verify product list is loading`() = runTest {

        val repository = Mockito.mock(ProductRepository::class.java)
        Mockito.`when`(repository.getProductList())
            .thenReturn(ServiceResponse.success(productList))

        launchFragmentInHiltContainer<ProductListFragment>() {
            val fragment = this as ProductListFragment
            assertNotNull(fragment.binding)
            val viewModel = ProductViewModel(repository)
            viewModel.getProductList()
            testDispatcher.scheduler.advanceUntilIdle()
            val result = viewModel.products.getOrAwaitValue()
            assertNotNull(result.data)
            assertEquals(productList, result.data)
        }
    }

    @Test
    fun `verify product list is empty`() = runTest {

        val repository = Mockito.mock(ProductRepository::class.java)
        Mockito.`when`(repository.getProductList())
            .thenReturn(ServiceResponse.success(emptyList()))

        launchFragmentInHiltContainer<ProductListFragment>() {
            val fragment = this as ProductListFragment
            assertNotNull(fragment.binding)
            val viewModel = ProductViewModel(repository)
            viewModel.getProductList()
            testDispatcher.scheduler.advanceUntilIdle()
            val result = viewModel.products.getOrAwaitValue()
            assertNotNull(result.data)
            assertEquals(0, result.data!!.size)
        }
    }

    @Test
    fun `verify product list with error`() = runTest {

        val repository = Mockito.mock(ProductRepository::class.java)
        Mockito.`when`(repository.getProductList())
            .thenReturn(ServiceResponse.error("Unknown error", null))

        launchFragmentInHiltContainer<ProductListFragment>() {
            val fragment = this as ProductListFragment
            assertNotNull(fragment.binding)
            val viewModel = ProductViewModel(repository)
            viewModel.getProductList()
            testDispatcher.scheduler.advanceUntilIdle()
            val result = viewModel.products.getOrAwaitValue()
            assertNotNull(result)
            assertEquals("Unknown error", result.message)
        }
    }

    @Test
    fun `verify product recycler view rendering`() = runTest {
        launchFragmentInHiltContainer<ProductListFragment>() {
            val fragment = this as ProductListFragment
            val recyclerView = fragment.binding.recyclerView
            val adapter = recyclerView.adapter as ProductListAdapter
            adapter.submitList(productList)
            recyclerView.measure(
                View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED
            )
            recyclerView.layout(0, 0, 1000, 1000)
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(0)
            assertNotNull(viewHolder)
            assertEquals(
                productList.get(0).title,
                (viewHolder as ProductListAdapter.ProductViewHolder).itemView.findViewById<TextView>(
                    R.id.txt_product_name
                ).text
            )
            assertTrue((viewHolder).itemView.hasOnClickListeners())
        }
    }



    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

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
}