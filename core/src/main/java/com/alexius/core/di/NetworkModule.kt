package com.alexius.core.di

import com.alexius.core.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import io.ktor.client.plugins.logging.Logger

val networkModule = module{
    single {
        HttpClient(CIO){
            install(ContentNegotiation){
                json(Json{
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging){
                logger = object : Logger{
                    override fun log(message: String) {
                        if (BuildConfig.DEBUG) {
                            println(message)
                        }
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
}