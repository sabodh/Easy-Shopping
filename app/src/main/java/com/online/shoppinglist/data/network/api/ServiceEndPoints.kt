package com.online.shoppinglist.data.network.api

import com.online.shoppinglist.data.network.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceEndPoints {

    @GET("/products")
    suspend fun getProductList(): Response<List<Product>>

    @GET("/products/{productId}")
    suspend fun getProductDetails(@Path("productId") productId: String): Response<Product>
}