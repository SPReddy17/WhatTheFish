package com.example.fish_interactors

import com.example.core.DataState
import com.example.core.ProgressBarState
import com.example.core.UIComponent
import com.example.fish_datasource_test.cache.FishCacheFake
import com.example.fish_datasource_test.cache.FishDatabaseFake
import com.example.fish_datasource_test.network.data.FishDataValid
import com.example.fish_datasource_test.network.serializeFishData
import com.example.fish_domain.Fish
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.random.Random

/**
 * 1. Success (Retrieve a hero from the cache successfully)
 * 2. Failure (The hero does not exist in the cache)
 */
class GetFishFromCacheTest {

    // system in test
    private lateinit var getFishFromCache: GetFishFromCache

    @Test
    fun getHeroFromCache_success() =  runBlocking {
        // setup
        val fishDatabase = FishDatabaseFake()
        val fishCache = FishCacheFake(fishDatabase)

        getFishFromCache = GetFishFromCache(fishCache)

        // insert fishes into the cache
        val fishData = serializeFishData(FishDataValid.data)
        fishCache.insert(fishData)

        // choose a hero at random
        val fish = fishData.get(Random.nextInt(0, fishData.size - 1))

        // Execute the use-case
        val emissions = getFishFromCache.execute(fish.scientificName?:"").toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<Fish>(ProgressBarState.Loading))

        // Confirm second emission is data from the cache
        assert(emissions[1] is DataState.Data)
        assert((emissions[1] as DataState.Data).data?.scientificName == fish.scientificName)
        assert((emissions[1] as DataState.Data).data?.speciesName == fish.speciesName)

        // Confirm loading state is IDLE
        assert(emissions[2] == DataState.Loading<Fish>(ProgressBarState.Idle))
    }

    @Test
    fun getHeroFromCache_fail() =  runBlocking {
        // setup
        val fishDatabase = FishDatabaseFake()
        val fishCache = FishCacheFake(fishDatabase)

        getFishFromCache = GetFishFromCache(fishCache)

        // insert fishes into the cache
        val fishData = serializeFishData(FishDataValid.data)
        fishCache.insert(fishData)

        // choose a hero at random and remove it from the cache
        val fish = fishData.get(Random.nextInt(0, fishData.size - 1))
        fishCache.removeFish(fish.scientificName?:"")

        // Execute the use-case
        val emissions = getFishFromCache.execute(fish.scientificName?:"").toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<Fish>(ProgressBarState.Loading))

        // Confirm second emission is error response
        assert(emissions[1] is DataState.Response)
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).title == "Error")
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).description.contains("That Fish does not exist in the cache"))

        // Confirm loading state is IDLE
        assert(emissions[2] == DataState.Loading<Fish>(ProgressBarState.Idle))
    }
}
