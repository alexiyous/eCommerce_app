package com.alexius.core.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    fun readAppEntry(): Flow<Boolean>
    suspend fun saveAppEntry(value: Boolean)
}