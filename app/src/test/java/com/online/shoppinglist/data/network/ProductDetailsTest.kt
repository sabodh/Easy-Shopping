package com.online.shoppinglist.data.network

import com.online.shoppinglist.data.network.api.ServiceEndPoints
import com.online.shoppinglist.utils.Helper
import com.online.shoppinglist.data.network.model.Product
import com.online.shoppinglist.domain.repository.model.Rating
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailsTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var serviceEndPoints: ServiceEndPoints
    private var PRODUCTID: Int = 2

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        serviceEndPoints = Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ServiceEndPoints::class.java)

    }

    /**
     * Test for success result
     */

    @Test
    fun `get success product details`() = runTest {
        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/product.json")
        mockResponse.setResponseCode(HttpURLConnection.HTTP_OK)
        mockResponse.setBody(content)
        mockServer.enqueue(mockResponse)
        val response = serviceEndPoints.getProductDetails(PRODUCTID.toString())
        mockServer.takeRequest()
        Assert.assertEquals(true, response.isSuccessful)
        Assert.assertNotNull(response.body())
        Assert.assertEquals(PRODUCTID, response.body()?.id)
        Assert.assertNotNull(response.body()?.title)

    }

    /**
     * Test for success result
     */

    @Test
    fun `get Product details properly`() = runTest {
        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/product.json")
        mockResponse.setResponseCode(HttpURLConnection.HTTP_OK)
        mockResponse.setBody(content)
        mockServer.enqueue(mockResponse)
        val response = serviceEndPoints.getProductDetails(PRODUCTID.toString())
        mockServer.takeRequest()
        val expected = Product(
            id = 2,
            title = "Mens Casual Premium Slim Fit T-Shirts ",
            price = 22.3,
            description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            rating = Rating()
            )
        Assert.assertEquals(true, response.isSuccessful)
        Assert.assertEquals(expected.title, response.body()?.title)
        Assert.assertEquals(expected.price, response.body()?.price)

    }

    /**
     * Test for failure 404 result
     */
    @Test
    fun `get response with error`() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockResponse.setBody("Something went wrong")
        mockServer.enqueue(mockResponse)
        val response = serviceEndPoints.getProductDetails(PRODUCTID.toString())
        mockServer.takeRequest()
        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(HttpURLConnection.HTTP_NOT_FOUND, response.code())

    }

    /**
     * Test for empty result
     */
    @Test
    fun `get empty response`() = runTest {
        val mockResponse = MockResponse()
        val content = "{}"
        mockResponse.setResponseCode(HttpURLConnection.HTTP_OK)
        mockResponse.setBody(content)
        mockServer.enqueue(mockResponse)
        val response = serviceEndPoints.getProductDetails(PRODUCTID.toString())
        mockServer.takeRequest()
        Assert.assertEquals(true, response.isSuccessful)
        Assert.assertNull(null, response.body()?.title)

    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}