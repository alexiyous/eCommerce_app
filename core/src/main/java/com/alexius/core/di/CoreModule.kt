package com.alexius.core.di

import org.koin.dsl.module

val coreModule = module{
    includes(
        networkModule,
        repositoryModule,
        useCaseModule,
        managerModule
    )
}