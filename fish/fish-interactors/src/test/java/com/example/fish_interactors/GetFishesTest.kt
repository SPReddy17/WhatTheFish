package com.example.fish_interactors

import com.example.core.DataState
import com.example.core.ProgressBarState
import com.example.core.UIComponent
import com.example.fish_datasource_test.cache.FishCacheFake
import com.example.fish_datasource_test.cache.FishDatabaseFake
import com.example.fish_datasource_test.network.FishServiceFake
import com.example.fish_datasource_test.network.FishServiceResponseType
import com.example.fish_datasource_test.network.data.FishDataValid
import com.example.fish_datasource_test.network.serializeFishData
import com.example.fish_domain.Fish
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetFishesTest {
    //system in test
    private lateinit var getFishes: GetFishes

    @Test
    fun getFishes_success() = runBlocking {

        //setup
        val fishDatabase = FishDatabaseFake()
        val fishCache = FishCacheFake(fishDatabase)
        val fishService = FishServiceFake().build(
            type = FishServiceResponseType.GoodData
        )
        getFishes = GetFishes(
            cache = fishCache,
            service = fishService
        )
        // confirm that the cache is empty, before any use-case is executed..
        var cachedFishes  = fishCache.selectAll()
        assert(cachedFishes.isEmpty())

        //execute the use case
        val emissions = getFishes.execute().toList()

        // first  emission should be loading
        assert(emissions[0] == DataState.Loading<List<Fish>>(ProgressBarState.Loading))

        // confirm the second emission, which is data...

        assert(emissions[1] is DataState.Data)
        assert((emissions[1] as DataState.Data).data?.size?:0 >1)

        // cache is no longer empty

        cachedFishes = fishCache.selectAll()
        assert(cachedFishes.size >1)

        // last  emission should be loading Idle
        assert(emissions[2] == DataState.Loading<List<Fish>>(ProgressBarState.Idle))
    }

    @Test
    fun getFishes_malformedData_successFromCache() = runBlocking {

        //setup
        val fishDatabase = FishDatabaseFake()
        val fishCache = FishCacheFake(fishDatabase)
        val fishService = FishServiceFake().build(
            type = FishServiceResponseType.Malformed
        )
        getFishes = GetFishes(
            cache = fishCache,
            service = fishService
        )

        // confirm that the cache is empty, before any use-case is executed..
        var cachedFishes  = fishCache.selectAll()
        assert(cachedFishes.isEmpty())

        // add some data to cache
        val fishData = serializeFishData(FishDataValid.data)
        fishCache.insert(fishData)

        cachedFishes  = fishCache.selectAll()
        assert(cachedFishes.isNotEmpty())

        //execute the use case
        val emissions = getFishes.execute().toList()

        // first  emission should be loading
        assert(emissions[0] == DataState.Loading<List<Fish>>(ProgressBarState.Loading))

        // confirm the second emission is error response
        assert(emissions[1] is DataState.Response)
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).title == "Network Data Error")
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).description.contains("Unexpected JSON token at offset"))


        // confirm third emission is data from cache
        assert(emissions[2] is DataState.Data)
        assert((emissions[2] as DataState.Data).data?.size?:0 > 1)


        // last  emission should be loading Idle
        assert(emissions[3] == DataState.Loading<List<Fish>>(ProgressBarState.Idle))
    }


    @Test
    fun getFishes_emptyList() = runBlocking {
        //setup
        val fishDatabase = FishDatabaseFake()
        val fishCache = FishCacheFake(fishDatabase)
        val fishService = FishServiceFake().build(
            type = FishServiceResponseType.EmptyList
        )
        getFishes = GetFishes(
            cache = fishCache,
            service = fishService
        )
        // confirm that the cache is empty, before any use-case is executed..
        var cachedFishes  = fishCache.selectAll()
        assert(cachedFishes.isEmpty())

        //execute the use case
        val emissions = getFishes.execute().toList()

        // first  emission should be loading
        assert(emissions[0] == DataState.Loading<List<Fish>>(ProgressBarState.Loading))

        // confirm the second emission, which is data...

        assert(emissions[1] is DataState.Data)
        assert((emissions[1] as DataState.Data).data?.size ==0 )

        // cache is no longer empty

        cachedFishes = fishCache.selectAll()
        assert(cachedFishes.isEmpty())

        // last  emission should be loading Idle
        assert(emissions[2] == DataState.Loading<List<Fish>>(ProgressBarState.Idle))
    }
}