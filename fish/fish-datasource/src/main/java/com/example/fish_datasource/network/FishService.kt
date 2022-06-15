package com.example.fish_datasource.network

import com.example.fish_domain.Fish
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

interface FishService {
    suspend fun getFishStats(): List<Fish>

    companion object Factory {
        fun build(): FishService {
            return FishServiceImpl(
                httpClient = HttpClient(Android) {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(
                            kotlinx.serialization.json.Json {
                                ignoreUnknownKeys = true
                                isLenient = true
                            }
                        )
                    }
                }
            )
        }
    }
}