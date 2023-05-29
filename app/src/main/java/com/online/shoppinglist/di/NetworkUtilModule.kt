package com.online.shoppinglist.di

import android.content.Context
import com.online.shoppinglist.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkUtilModule {

    @Provides
    fun getNetworkUtil(@ApplicationContext context: Context): NetworkUtils{
        return NetworkUtils(context)
    }
}