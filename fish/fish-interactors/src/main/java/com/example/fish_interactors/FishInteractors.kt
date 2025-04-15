package com.example.fish_interactors

import com.example.fish_datasource.cache.FishCache
import com.example.fish_datasource.network.FishService
import com.squareup.sqldelight.db.SqlDriver

data class FishInteractors(
    val getFishes: GetFishes,
    val getFishFromCache: GetFishFromCache,
    val filterFishes: FilterFishes
    ) {
    companion object Factory {


        fun build(sqlDriver :SqlDriver): FishInteractors {
            val service = FishService.build()
            val cache  = FishCache.build(sqlDriver)
            return FishInteractors(
                getFishes = GetFishes(
                    service = service,
                    cache = cache
                ),
                getFishFromCache =  GetFishFromCache(
                    cache = cache
                ),
                filterFishes = FilterFishes()
            )
        }
        val schema: SqlDriver.Schema = FishCache.schema

        val dbName: String = FishCache.dbName
    }

}