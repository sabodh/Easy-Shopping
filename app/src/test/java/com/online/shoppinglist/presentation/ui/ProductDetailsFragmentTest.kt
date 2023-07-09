package com.online.shoppinglist.presentation.ui

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.online.shoppinglist.data.network.model.Product
import com.online.shoppinglist.domain.repository.model.Rating
import com.online.shoppinglist.domain.repository.ProductRepository
import com.online.shoppinglist.launchFragmentInHiltContainer
import com.online.shoppinglist.presentation.viewmodel.ProductViewModel
import com.online.shoppinglist.utils.NetworkUtils
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
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class ProductDetailsFragmentTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    lateinit var viewModel: ProductViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `verify data binding properly`() {
        val product = getProduct()
        val bundle = Bundle()
        bundle.putParcelable("selectedProduct", product)
        launchFragmentInHiltContainer<ProductDetailsFragment>(bundle) {
            val fragment = this as ProductDetailsFragment
            assertNotNull(fragment.binding)
            fragment.setProductDetails(fragment.binding, product)
            assert(fragment.binding.txtAmount.text == "£9.99")
            assert(fragment.binding.txtCategory.text == "Test Category")
            assert(fragment.binding.txtSummary.text == "Test Description")
            assert(fragment.binding.txtInfoHeader.text == "Test Product")
        }
    }

    @Test
    fun `verify product detail is loading`() = runTest {
        val product = getProduct()
        val repository = mock(ProductRepository::class.java)
        Mockito.`when`(repository.getProductDetails("2"))
            .thenReturn(ServiceResponse.success(product))
        viewModel = ProductViewModel(repository)

        val bundle = Bundle()
        bundle.putParcelable("selectedProduct", product)
        launchFragmentInHiltContainer<ProductDetailsFragment>(bundle) {
            val fragment = this as ProductDetailsFragment
            assertNotNull(fragment.binding)
            viewModel.getProductDetails("2")
            testDispatcher.scheduler.advanceUntilIdle()
            val result = viewModel.product.getOrAwaitValue()
            assertNotNull(result)
            assertEquals(result.data!!.id, product.id)
        }
    }

    @Test
    fun `verify product detail is null`() = runTest {
        val product = getProduct()
        val repository = mock(ProductRepository::class.java)
        Mockito.`when`(repository.getProductDetails("2"))
            .thenReturn(ServiceResponse.success(null))
        viewModel = ProductViewModel(repository)

        val bundle = Bundle()
        bundle.putParcelable("selectedProduct", product)
        launchFragmentInHiltContainer<ProductDetailsFragment>(bundle) {
            val fragment = this as ProductDetailsFragment
            assertNotNull(fragment.binding)
            viewModel.getProductDetails("2")
            testDispatcher.scheduler.advanceUntilIdle()
            val result = viewModel.product.getOrAwaitValue()
            assertNull(result.data)

        }
    }

    @Test
    fun `verify product detail is error`() = runTest {
        val product = getProduct()
        val repository = mock(ProductRepository::class.java)
        Mockito.`when`(repository.getProductDetails("2"))
            .thenReturn(ServiceResponse.error("Unknown Error", null))
        viewModel = ProductViewModel(repository)

        val bundle = Bundle()
        bundle.putParcelable("selectedProduct", product)
        launchFragmentInHiltContainer<ProductDetailsFragment>(bundle) {
            val fragment = this as ProductDetailsFragment
            assertNotNull(fragment.binding)
            viewModel.getProductDetails("2")
            testDispatcher.scheduler.advanceUntilIdle()
            val result = viewModel.product.getOrAwaitValue()
            assertNull(result.data)
            assertEquals("Unknown Error", result.message)

        }
    }

    @Test
    fun `test showSnackbar when network is not connected`() {

        val networkutil = mock(NetworkUtils::class.java)
        Mockito.`when`(networkutil.isNetworkConnected())
            .thenReturn(false)

        val product = getProduct()
        val bundle = Bundle()
        bundle.putParcelable("selectedProduct", product)
        launchFragmentInHiltContainer<ProductDetailsFragment>(bundle) {
            val fragment = this as ProductDetailsFragment
            assertNotNull(fragment.binding)
            fragment.checkNetworkConnectivity()


        }

    }

    fun getProduct(): Product {
        return Product(
            category = "Test Category",
            description = "Test Description",
            id = 2,
            image = "https://fakestoreapi.com/img/1.jpg",
            price = 9.99,
            title = "Test Product",
            rating = Rating()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()

    }


}