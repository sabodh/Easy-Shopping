package com.online.shoppinglist.presentation.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.online.shoppinglist.data.network.model.Product
import com.online.shoppinglist.domain.usecases.GetProductDetailsUseCase
import com.online.shoppinglist.domain.usecases.GetProductsUseCase
import com.online.shoppinglist.utils.ServiceResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productsUseCase: GetProductsUseCase,
    private val productDetailsUseCase: GetProductDetailsUseCase
) : ViewModel() {

    // Used to list the products
    private var _products = MutableLiveData<ServiceResponse<List<Product>>>()
    val products get() = _products

    // Used to list the selected product details
    private var _product = MutableLiveData<ServiceResponse<Product>>()
    val product get() = _product

    init {
        getProductList()
    }

    /**
     * Used to get the product details from server
     * based on the given productId
     */
    fun getProductDetails(productId: String) {
        viewModelScope.launch {
            _product.postValue(ServiceResponse.loading(null))
            val response = productDetailsUseCase(productId)
            _product.postValue(response)
        }
    }

    /**
     * Used to get the product list from server
     */
    fun getProductList() {
        viewModelScope.launch {
            _products.postValue(ServiceResponse.loading(null))
            val response = productsUseCase()
            _products.postValue(response)
        }
    }
}