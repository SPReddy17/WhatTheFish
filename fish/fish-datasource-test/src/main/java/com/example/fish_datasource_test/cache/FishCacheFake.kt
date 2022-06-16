package com.example.fish_datasource_test.cache

import com.example.fish_datasource.cache.FishCache
import com.example.fish_domain.Fish

class FishCacheFake(
    private val db: FishDatabaseFake
) :FishCache {
    override suspend fun getFish(scientificName: String): Fish? {
        return db.fishes.find { it.scientificName == scientificName }

    }

    override suspend fun removeFish(scientificName: String) {
        db.fishes.removeIf { it.scientificName == scientificName }
    }

    override suspend fun selectAll(): List<Fish> {
        return db.fishes
    }

    override suspend fun insert(fish: Fish) {
        if(db.fishes.isNotEmpty()){
            var didInsert = false
            for(f in db.fishes){
                if(f.scientificName == fish.scientificName){
                    db.fishes.remove(f)
                    db.fishes.add(fish)
                    didInsert = true
                    break
                }
            }
            if(!didInsert){
                db.fishes.add(fish)
            }
        }
        else{
            db.fishes.add(fish)
        }
    }

    override suspend fun insert(fishes: List<Fish>) {
        if(db.fishes.isNotEmpty()){
            for(fish in fishes){
                if(db.fishes.contains(fish)){
                    db.fishes.remove(fish)
                    db.fishes.add(fish)
                }
            }
        }
        else{
            db.fishes.addAll(fishes)
        }
    }

    override suspend fun searchByName(localizedName: String): List<Fish> {
        return db.fishes.find { it.speciesName == localizedName }?.let {
            listOf(it)
        }?: listOf()
    }
}