package com.alexius.core.domain.usecase

import com.alexius.core.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(value: Boolean) = localUserManager.saveAppEntry(value)
}