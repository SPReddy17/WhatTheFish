package com.example.fish_datasource.cache

import com.example.fish_domain.Fish
import com.example.fishdatasource.cache.Fish_Entity

fun Fish_Entity.toFish(): Fish {
    return Fish(
        animalHealth = animalHealth,
        availability = availability,
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
        environmentalConsiderations = environmentalConsiderations,
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
        population = population,
        populationStatus = populationStatus,
        production = production,
        protein = protien,
        quote = quote,
        quoteBackgroundColor = quoteBackgroundColor,
        research = research,
        saturatedFattyAcidsTotal = saturatedFattyAcidsTotal,
        scientificName = scientificName,
        selenium = selenium,
        servingWeight = servingWeight,
        servings = servings,
        sodium = sodium,
        source = source,
        speciesAliases = speciesAliases,
        speciesIllustrationPhoto = speciesIllustrationPhoto,
        speciesName = speciesName,
        sugarsTotal = sugarsTotal,
        taste = taste,
        texture = texture,
        last_update = last_update
    )
}