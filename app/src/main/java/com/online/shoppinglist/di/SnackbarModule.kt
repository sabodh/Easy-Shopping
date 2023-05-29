package com.online.shoppinglist.di

import com.google.android.material.snackbar.Snackbar
import com.online.shoppinglist.utils.SnackbarUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SnackbarModule {
    @Provides
    @Singleton
    fun showSnackbar(): SnackbarUtil{
        return SnackbarUtil()
    }
}