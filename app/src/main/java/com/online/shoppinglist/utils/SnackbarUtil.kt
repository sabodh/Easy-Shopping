package com.online.shoppinglist.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class SnackbarUtil @Inject constructor() {

    fun showSnackbar(view : View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}