package com.online.shoppinglist.di

import android.content.Context
import com.online.shoppinglist.utils.ScreenUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScreenUtilModule {

    @Provides
    @Singleton
    fun provideScreenUtil(@ApplicationContext context: Context):ScreenUtils{
        return ScreenUtils(context)
    }

}