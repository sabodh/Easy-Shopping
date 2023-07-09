package com.online.shoppinglist.data.repository

import com.online.shoppinglist.data.network.model.Product
import com.online.shoppinglist.data.network.api.ServiceEndPoints
import com.online.shoppinglist.domain.repository.ProductRepository
import com.online.shoppinglist.utils.ServiceResponse
import java.net.UnknownHostException
import javax.inject.Inject

class ProductRepositoryImpl
@Inject constructor(private val serviceEndPoints: ServiceEndPoints) : ProductRepository {

    override suspend fun getProductList(): ServiceResponse<List<Product>> {
        return try {
            val response = serviceEndPoints.getProductList()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let ServiceResponse.success(it)
                } ?: ServiceResponse.error("Unknown error", null)
            } else {
                ServiceResponse.error(response.errorBody()?.string() ?: "Unknown Error", null)

            }
        } catch (e: UnknownHostException) {
            ServiceResponse.error("Network Connection Lost!.", null)
        } catch (e: java.lang.Exception) {
            ServiceResponse.error(e.message.toString() ?: "Unknown Error", null)

        }
    }

    override suspend fun getProductDetails(productId: String): ServiceResponse<Product> {
        return try {
            val response = serviceEndPoints.getProductDetails(productId)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let ServiceResponse.success(it)
                } ?: ServiceResponse.error("Unknown error", null)
            } else {
                ServiceResponse.error(response.errorBody()?.string() ?: "Unknown Error", null)
            }
        } catch (e: UnknownHostException) {
            ServiceResponse.error("Network Connection Lost!.", null)
        } catch (e: java.lang.Exception) {
            ServiceResponse.error(e.message.toString() ?: "Unknown Error", null)

        }
    }


}