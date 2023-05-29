package com.online.shoppinglist.presentation.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.online.shoppinglist.data.model.Product
import com.online.shoppinglist.domain.repository.ProductRepository
import com.online.shoppinglist.utils.ServiceResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
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
            val response = repository.getProductDetails(productId)
            _product.postValue(response)
        }
    }

    /**
     * Used to get the product list from server
     */
    fun getProductList() {
        viewModelScope.launch {
            _products.postValue(ServiceResponse.loading(null))
            val response = repository.getProductList()
            _products.postValue(response)
        }
    }
}