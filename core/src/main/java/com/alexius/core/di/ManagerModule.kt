package com.alexius.core.di

import android.app.Application
import com.alexius.core.data.manager.LocalUserManagerImpl
import com.alexius.core.domain.manager.LocalUserManager
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

val managerModule = module{
    single<LocalUserManager>{
        LocalUserManagerImpl(androidContext() as Application)
    }
}