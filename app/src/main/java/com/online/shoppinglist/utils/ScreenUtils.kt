package com.online.shoppinglist.utils

import android.content.Context
import android.content.res.Configuration
import javax.inject.Inject

class ScreenUtils @Inject constructor(private val context: Context) {

    fun getGridCount(): Int {
        val screenSize =
            context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        val isTablet =
            screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE
        val orientation = context.resources.configuration.orientation
        val isLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE
        return if (isTablet && isLandscape) {
            5  // Tablet in landscape mode
        } else if (isTablet && !isLandscape) {
            3  // Tablet in portrait mode
        } else if (!isTablet && isLandscape) {
            4  // Phone in landscape mode
        } else {
            2  // Phone in portrait mode
        }
    }
}