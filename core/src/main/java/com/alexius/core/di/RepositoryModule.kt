package com.alexius.core.di

import com.alexius.core.data.repository.ProductRepositoryImpl
import com.alexius.core.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module{
    single<ProductRepository>{
        ProductRepositoryImpl(get())
    }
}