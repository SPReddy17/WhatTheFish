package com.example.fish_datasource_test.network

import com.example.fish_datasource.network.FishService
import com.example.fish_datasource.network.FishServiceImpl
import com.example.fish_datasource_test.network.data.FishDataEmpty
import com.example.fish_datasource_test.network.data.FishDataMalformed
import com.example.fish_datasource_test.network.data.FishDataValid
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*

class FishServiceFake {

    private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
    private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

    fun build(
        type: FishServiceResponseType
    ): FishService {
        val client = HttpClient(MockEngine) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            engine {
                addHandler { request ->
                    when (request.url.fullUrl) {
                        "https://www.fishwatch.gov/api/species" -> {
                            val responseHeaders = headersOf(
                                "Content-Type" to listOf("application/json", "charset=utf-8")
                            )
                            when (type) {
                                is FishServiceResponseType.EmptyList -> {
                                    respond(
                                        FishDataEmpty.data,
                                        status = HttpStatusCode.OK,
                                        headers = responseHeaders
                                    )
                                }
                                is FishServiceResponseType.Malformed -> {
                                    respond(
                                        FishDataMalformed.data,
                                        status = HttpStatusCode.OK,
                                        headers = responseHeaders
                                    )
                                }
                                is FishServiceResponseType.GoodData -> {
                                    respond(
                                        FishDataValid.data,
                                        status = HttpStatusCode.OK,
                                        headers = responseHeaders
                                    )
                                }
                                is FishServiceResponseType.Http404 -> {
                                    respond(
                                        FishDataEmpty.data,
                                        status = HttpStatusCode.NotFound,
                                        headers = responseHeaders
                                    )
                                }
                            }
                        }
                        else -> error("Unhandled ${request.url.fullUrl}")
                    }
                }
            }
        }
        return FishServiceImpl(client)
    }
}
