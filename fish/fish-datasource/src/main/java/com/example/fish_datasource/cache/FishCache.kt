package com.example.fish_datasource.cache

import com.example.fish_domain.Fish
import com.example.fish_domain.SpeciesIllustrationPhoto
import com.example.fishdatasource.cache.Fish_Entity
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver

interface FishCache {

    suspend fun getFish(scientificName: String): Fish?

    suspend fun removeFish(scientificName: String)

    suspend fun selectAll(): List<Fish>

    suspend fun insert(fish: Fish)

    suspend fun insert(fishes: List<Fish>)

    suspend fun searchByName(localizedName: String): List<Fish>


    // Can select multiple roles
//    suspend fun searchByRole(
//        carry: Boolean = false,
//        escape: Boolean = false,
//        nuker: Boolean = false,
//        initiator: Boolean = false,
//        durable: Boolean = false,
//        disabler: Boolean = false,
//        jungler: Boolean = false,
//        support: Boolean = false,
//        pusher: Boolean = false,
//    ): List<Fish>


    companion object Factory {
        private val speciesIllustrationAdapter =
            object : ColumnAdapter<SpeciesIllustrationPhoto, String> {
                override fun decode(databaseValue: String): SpeciesIllustrationPhoto {
                    val split = databaseValue.split(",")
                    return SpeciesIllustrationPhoto(split[0], split[1], split[2])
                }

                override fun encode(value: SpeciesIllustrationPhoto): String {
                    return "${value.alt},${value.src},${value.title}"
                }
            }

        fun build(sqlDriver: SqlDriver): FishCache {
            return FishCacheImpl(
                FishDatabase(
                    sqlDriver,
                    fish_EntityAdapter = Fish_Entity.Adapter(
                        speciesIllustrationAdapter
                    )
                )
            )
        }

        val schema: SqlDriver.Schema = FishDatabase.Schema

        val dbName: String = "fishes.db"
    }

}