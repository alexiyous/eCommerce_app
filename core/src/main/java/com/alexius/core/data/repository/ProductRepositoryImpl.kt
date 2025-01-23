package com.alexius.core.data.repository

import android.util.Log
import com.alexius.core.data.model.remote.toProduct
import com.alexius.core.domain.model.Product
import com.alexius.core.domain.network.NetworkService
import com.alexius.core.domain.repository.ProductRepository
import com.alexius.core.util.UiState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException

class ProductRepositoryImpl(
    private val networkService: NetworkService
) : ProductRepository {

    private val auth = Firebase.auth

    override fun getProducts(): Flow<UiState<List<Product>>> = flow {
        try {
            emit(UiState.Loading)
            val products = networkService.getProducts().map { it.toProduct() }
            emit(UiState.Success(products))
        } catch (e: ClientRequestException){
            emit(UiState.Error(e.message))
        } catch (e: ServerResponseException){
            emit(UiState.Error(e.message))
        } catch (e: IOException){
            emit(UiState.Error(e.message?:"IOException"))
        } catch (e: Exception){
            emit(UiState.Error(e.message?:"Unknown Error"))
        }
    }

    override fun signInWithEmailPassword(
        email: String,
        password: String
    ): Flow<UiState<Boolean>> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(UiState.Success(true))
                } else {
                    trySend(UiState.Error(task.exception?.message ?: "Unknown error"))
                    Log.d(
                        "AuthenticationManager",
                        "createAccountWithEmailPassword: ${task.exception?.message}"
                    )
                }
            }
        awaitClose()
    }
}