package com.example.fish_datasource.cache

import com.example.fish_domain.Fish
import com.example.fishdatasource.cache.FishDbQueries

class FishCacheImpl(
    private val fishDatabase: FishDatabase
) :FishCache{

    private var queries : FishDbQueries = fishDatabase.fishDbQueries

    override suspend fun getFish(scientificName: String): Fish {
        return queries.getHero(scientificName).executeAsOne().toFish()
    }

    override suspend fun removeFish(scientificName: String) {
        queries.removeHero(scientificName)
    }

    override suspend fun selectAll(): List<Fish> {
        return queries.selectAll().executeAsList().map { it.toFish() }
    }

    override suspend fun insert(fish: Fish) {
        return fish.run {
            scientificName?.let {
                queries.insertFish(
                    animalHealth = animalHealth,
                    availability=availability,
                    biology = biology,
                    byCatch = byCatch,
                    calories = calories,
                    carbohydrate = carbohydrate,
                    cholesterol = cholesterol,
                    color = color,
                    diseaseTreatmentAndPrevention = diseaseTreatmentAndPrevention,
                    diseasesInSalmon = diseasesInSalmon,
                    displayedSeafoodProfileIllustration = displayedSeafoodProfileIllustration,
                    ecosystemServices = ecosystemServices,
                    environmentalConsiderations= environmentalConsiderations,
                    environmentalEffects = environmentalEffects,
                    farmingMethods = farmingMethods,
                    farmingMethods_ = farmingMethods_,
                    fatTotal = fatTotal,
                    feeds = feeds,
                    feeds_ = feeds_,
                    fiberTotalDietary = fiberTotalDietary,
                    fisheryManagement = fisheryManagement,
                    fishingRate = fishingRate,
                    habitat = habitat,
                    habitatImpacts = habitatImpacts,
                    harvest = harvest,
                    harvestType = harvestType,
                    healthBenefits = healthBenefits,
                    humanHealth = humanHealth,
                    human_Health_ = human_Health_,
                    //       imageGallery = imageGallery,
                    location = location,
                    management = management,
                    NOAAFisheriesRegion = NOAAFisheriesRegion,
                    path = path,
                    physicalDescription = physicalDescription,
                    population =population,
                    populationStatus =populationStatus,
                    production = production,
                    protien =protein,
                    quote = quote,
                    quoteBackgroundColor = quoteBackgroundColor,
                    research = research,
                    saturatedFattyAcidsTotal = saturatedFattyAcidsTotal,
                    scientificName = it,
                    selenium= selenium,
                    servingWeight = servingWeight,
                    servings =servings,
                    sodium= sodium,
                    source = source,
                    speciesAliases = speciesAliases,
                    speciesIllustrationPhoto = speciesIllustrationPhoto,
                    speciesName= speciesName,
                    sugarsTotal = sugarsTotal,
                    taste = taste,
                    texture = texture,
                    last_update = last_update
                )
            }
        }
    }

    override suspend fun insert(fishes: List<Fish>) {
        for(fish in fishes){
            try {
                insert(fish)
            }catch (e: Exception){
                e.printStackTrace()
                // if one has an error just continue with others
            }
        }
    }

    override suspend fun searchByName(localizedName: String): List<Fish> {
        TODO("Not yet implemented")
    }
}