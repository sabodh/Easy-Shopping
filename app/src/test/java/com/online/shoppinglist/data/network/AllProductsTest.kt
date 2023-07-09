package com.online.shoppinglist.data.network

import com.online.shoppinglist.data.network.api.ServiceEndPoints
import com.online.shoppinglist.utils.Helper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class AllProductsTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var serviceEndPoints: ServiceEndPoints

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
    fun `get success list from server`() = runTest{
        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/productList.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockServer.enqueue(mockResponse)
        val response = serviceEndPoints.getProductList()
        mockServer.takeRequest()
        assertEquals(true, response.isSuccessful)
        assertNotNull(response.body())

    }
    /**
     * Test for success result
     */
    @Test
    fun `verify product list url is valid`() = runTest{
        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/productList.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockServer.enqueue(mockResponse)
        val response = serviceEndPoints.getProductList()
        val request = mockServer.takeRequest()
        assertEquals("/products", request.path)
        assertEquals(true, response.isSuccessful)
        assertNotNull(response.body())

    }
    /**
     * Test for failure 404 result
     */
    @Test
    fun `get error message from server`() = runTest{
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("Something went wrong")
        mockServer.enqueue(mockResponse)
        val response = serviceEndPoints.getProductList()
        mockServer.takeRequest()
        assertEquals(false, response.isSuccessful)
        assertEquals(404, response.code())

    }
    /**
     * Test for empty result
     */
    @Test
    fun `get empty list from server`() = runTest{
        val mockResponse = MockResponse()
        val content = "[]"
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockServer.enqueue(mockResponse)
        val response = serviceEndPoints.getProductList()
        mockServer.takeRequest()
        assertEquals(true, response.isSuccessful)
        assertNotNull(response.body())

    }
    /**
     * Test for empty result
     */
    @Test
    fun `verify product details url is valid`() = runTest{
        val mockResponse = MockResponse()
        val content = "[]"
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockServer.enqueue(mockResponse)
        val response = serviceEndPoints.getProductList()
        val request = mockServer.takeRequest()
        assertEquals("/products", request.path)
        assertEquals(true, response.isSuccessful)
        assertNotNull(response.body())
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}