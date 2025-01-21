package com.alexius.core.di

import com.alexius.core.domain.usecase.GetProductsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetProductsUseCase(get()) }
}