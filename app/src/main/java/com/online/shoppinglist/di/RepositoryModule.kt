package com.online.shoppinglist.di

import com.online.shoppinglist.data.repository.ProductRepositoryImpl
import com.online.shoppinglist.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        repositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}