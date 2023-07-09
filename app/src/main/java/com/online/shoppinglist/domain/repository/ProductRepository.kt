package com.online.shoppinglist.domain.repository

import com.online.shoppinglist.data.network.model.Product
import com.online.shoppinglist.utils.ServiceResponse

interface ProductRepository {

    suspend fun getProductList(): ServiceResponse<List<Product>>

    suspend fun getProductDetails(productId: String): ServiceResponse<Product>
}