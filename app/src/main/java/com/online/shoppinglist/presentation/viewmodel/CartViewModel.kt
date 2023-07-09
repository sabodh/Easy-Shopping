package com.online.shoppinglist.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.online.shoppinglist.data.network.model.Product

class CartViewModel : ViewModel() {

    private var _carts = MutableLiveData<MutableList<Product>>()
    val carts get() = _carts

    fun addItemsToCart(product: Product) {
        val currentList = _carts.value?.toMutableList() ?: mutableListOf()
        currentList.add(product)
        _carts.value = currentList
    }
    fun removeItemsFromCart(product: Product) {
        val currentList = _carts.value?.toMutableList() ?: mutableListOf()
        currentList.remove(product)
        _carts.value = currentList
    }

    fun getCartItemsCategoryGroups(): Map<String, List<Product>> {
        val listItems = carts.value?.toList()!!
        return listItems
            .groupBy { it.category }
    }


    fun setCartItemSum(list: List<Product>) {
        val currentList = _carts.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list)
        _carts.value = currentList
        val totalItems = currentList.sumOf { it.quantity!! }
        val totalPrice = currentList.sumOf { it.quantity!! * it.price }
        Log.d("Test", "totalItems ${totalItems}")
        Log.d("Test", "totalPrice ${totalPrice}")

    }
    fun getCartTotalItems(): Int {
        val currentList = _carts.value?.toMutableList() ?: mutableListOf()
        return currentList.sumOf { it.quantity!! }
    }
    fun getCartTotalValue(): Double {
        val currentList = _carts.value?.toMutableList() ?: mutableListOf()
        return currentList.sumOf { it.quantity!! * it.price }
    }

    fun setCartItems(list: List<Product>) {
        val currentList = _carts.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list)
        _carts.value = currentList
    }
}

