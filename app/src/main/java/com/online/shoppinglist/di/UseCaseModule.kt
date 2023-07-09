package com.online.shoppinglist.di

import com.online.shoppinglist.domain.repository.ProductRepository
import com.online.shoppinglist.domain.usecases.GetProductDetailsUseCase
import com.online.shoppinglist.domain.usecases.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun bindProductUseCase(repository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }
    @Provides
    fun bindProductDetailsUseCase(repository: ProductRepository): GetProductDetailsUseCase {
        return GetProductDetailsUseCase(repository)
    }

}