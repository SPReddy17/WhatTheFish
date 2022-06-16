package com.example.fish_datasource_test.network

import com.example.fish_datasource.network.FishDto
import com.example.fish_datasource.network.toFish
import com.example.fish_domain.Fish
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
}

fun serializeFishData(jsonData: String): List<Fish> {
    return json.decodeFromString<List<FishDto>>(jsonData).map { it.toFish() }

}