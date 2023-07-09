package com.online.shoppinglist.domain.usecases

import com.online.shoppinglist.domain.repository.ProductRepository
import com.online.shoppinglist.data.network.model.Product
import com.online.shoppinglist.utils.ServiceResponse
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val productRepository: ProductRepository)
{
    // The invoke function in Kotlin allows you to invoke an object as
    // if it were a regular function, which can make the code more concise and readable.
    suspend operator fun invoke() :ServiceResponse<List<Product>> {
        return productRepository.getProductList()
    }
}