package com.online.shoppinglist.data.network.model

import android.os.Parcelable
import com.online.shoppinglist.domain.repository.model.Rating
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String,
    val rating: Rating,
    val quantity: Int? = 1
): Parcelable