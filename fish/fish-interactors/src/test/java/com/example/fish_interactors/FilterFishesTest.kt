package com.example.fish_interactors

import com.example.core.FilterOrder
import com.example.fish_datasource_test.network.data.FishDataValid
import com.example.fish_datasource_test.network.serializeFishData
import com.example.fish_domain.FishFilter
import org.junit.Test
import kotlin.math.round

/**
 * 1. Success (Search for specific fish by 'scientificName' param)
 * 2. Success (Order by 'scientificName' param DESC)
 * 3. Success (Order by 'scientificName' param ASC)

 */
class FilterFishesTest {

    // System in test
    private lateinit var filterFishes: FilterFishes

    // Data
    private val fishList = serializeFishData(FishDataValid.data)

    @Test
    fun searchHeroByName(){
        filterFishes = FilterFishes()

        // Execute use-case
        val emissions = filterFishes.execute(
            current = fishList,
            fishName = "Cobia",
            fishFilter = FishFilter.Fish(),
        )

        // confirm it returns a single hero
        assert(emissions[0].speciesName == "Cobia")
    }

    @Test
    fun orderByNameDesc(){
        filterFishes = FilterFishes()

        // Execute use-case
        val emissions = filterFishes.execute(
            current = fishList,
            fishName = "",
            fishFilter = FishFilter.Fish(order = FilterOrder.Descending),
        )

        // confirm they are ordered Z-A
        for(index in 1 until emissions.size){
            assert(
                emissions[index-1].scientificName?.toCharArray()?.get(0)!! >= (emissions[index].scientificName?.toCharArray()?.get(0 )!!)
            )
        }
    }

    @Test
    fun orderByNameAsc(){
        filterFishes = FilterFishes()

        // Execute use-case
        val emissions = filterFishes.execute(
            current = fishList,
            fishName = "",
            fishFilter = FishFilter.Fish(order = FilterOrder.Ascending),
        )

        // confirm they are ordered A-Z
        for(index in 1..emissions.size - 1){
            emissions[index-1].speciesName?.toCharArray()?.get(0)!! <= (emissions[index].speciesName?.toCharArray()?.get(0 )!!)
        }
    }
}





