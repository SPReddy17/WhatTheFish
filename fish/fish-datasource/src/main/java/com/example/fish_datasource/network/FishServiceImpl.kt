package com.example.fish_datasource.network

import com.example.fish_domain.Fish
import io.ktor.client.*
import io.ktor.client.request.*

class FishServiceImpl(
    private val httpClient: HttpClient
) : FishService {
    override suspend fun getFishStats(): List<Fish> {
        return httpClient.get<List<FishDto>> {
            url(EndPoints.FISH_STATS)
        }.map { it.toFish() }
    }
}