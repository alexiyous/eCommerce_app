package com.alexius.core.data.network

import com.alexius.core.data.model.remote.ProductsDtoItem
import com.alexius.core.domain.network.NetworkService
import com.alexius.core.util.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.utils.io.InternalAPI

class NetworkServiceImpl(val client: HttpClient): NetworkService {
    override suspend fun getProducts(): List<ProductsDtoItem> {
        return makeWebRequest(
            url = "https://fakestoreapi.com/products",
            method = HttpMethod.Get,
            mapper = { dataModels: List<ProductsDtoItem> ->
                dataModels
            }
        )
    }

    @OptIn(InternalAPI::class)
    suspend inline fun <reified T, R> makeWebRequest(
        url: String,
        method: HttpMethod,
        body:Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap(),
        noinline mapper: ((T) -> R)? = null
    ): R {
        val response = client.request(url) {
            this.method = method

            // Apply query parameters
            url {
                this.parameters.appendAll(Parameters.build {
                    parameters.forEach { (key, value) -> append(key, value) }
                })
            }

            // Apply headers
            headers.forEach{ (key, value) -> this.headers.append(key, value) }

            // Set body for POST, PUT, etc
            if (body != null) {
                this.body = body
            }

            // Set content type
            contentType(ContentType.Application.Json)
        }.body<T>()
        return (mapper?.invoke(response) ?: response) as R
    }
}