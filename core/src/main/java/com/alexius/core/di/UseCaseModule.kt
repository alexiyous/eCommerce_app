package com.alexius.core.di

import com.alexius.core.domain.usecase.CreateInitUserFirestore
import com.alexius.core.domain.usecase.GetProductsUseCase
import com.alexius.core.domain.usecase.GetUserInfo
import com.alexius.core.domain.usecase.ReadAppEntry
import com.alexius.core.domain.usecase.ResetPassword
import com.alexius.core.domain.usecase.SaveAppEntry
import com.alexius.core.domain.usecase.SignInWithEmail
import com.alexius.core.domain.usecase.SignUpWithEmail
import com.alexius.core.domain.usecase.UploadImageProfile
import org.koin.dsl.module

val useCaseModule = module {
    single { GetProductsUseCase(get()) }
    single { ReadAppEntry(get()) }
    single { SignInWithEmail(get()) }
    single{ SignUpWithEmail(get()) }
    single { CreateInitUserFirestore(get()) }
    single { SaveAppEntry(get()) }
    single { ResetPassword(get()) }
    single { GetUserInfo(get()) }
    single { UploadImageProfile(get()) }
}