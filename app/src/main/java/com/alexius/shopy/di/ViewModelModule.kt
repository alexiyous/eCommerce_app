package com.alexius.shopy.di

import com.alexius.shopy.presentation.MainViewModel
import com.alexius.shopy.presentation.home.HomeViewModel
import com.alexius.shopy.presentation.sign_in.SignInViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel{
        HomeViewModel(get())
    }
    viewModel{
        MainViewModel(get())
    }
    viewModel{
        SignInViewModel(get())
    }
}