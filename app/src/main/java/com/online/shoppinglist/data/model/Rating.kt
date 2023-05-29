package com.online.shoppinglist.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(
    val count: Int? = 0,
    val rate: Double? = 0.00
):Parcelable