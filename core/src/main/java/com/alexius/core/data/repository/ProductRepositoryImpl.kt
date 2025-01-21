package com.alexius.core.data.repository

import com.alexius.core.data.model.remote.toProduct
import com.alexius.core.domain.model.Product
import com.alexius.core.domain.network.NetworkService
import com.alexius.core.domain.repository.ProductRepository
import com.alexius.core.util.UiState
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException

class ProductRepositoryImpl(
    private val networkService: NetworkService
) : ProductRepository {
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
}