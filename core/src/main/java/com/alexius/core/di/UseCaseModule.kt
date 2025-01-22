package com.alexius.core.di

import com.alexius.core.domain.usecase.GetProductsUseCase
import com.alexius.core.domain.usecase.ReadAppEntry
import org.koin.dsl.module

val useCaseModule = module {
    single { GetProductsUseCase(get()) }
    single { ReadAppEntry(get()) }
}