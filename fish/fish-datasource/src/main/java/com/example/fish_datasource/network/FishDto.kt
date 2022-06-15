package com.example.fish_datasource.network

import com.example.fish_domain.Fish
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FishDto(
    @SerialName("Animal Health")
    val animalHealth: String? = null,
    @SerialName("Availability")
    val availability: String? = null,
    @SerialName("Biology")
    val biology: String? = null,
    @SerialName("Bycatch")
    val byCatch: String? = null,
    @SerialName("Calories")
    val calories: String? = null,
    @SerialName("Carbohydrate")
    val carbohydrate: String? = null,
    @SerialName("Cholesterol")
    val cholesterol: String? = null,
    @SerialName("Color")
    val color: String? = null,
    @SerialName("Disease Treatment and Prevention")
    val diseaseTreatmentAndPrevention: String? = null,
    @SerialName("Diseases in Salmon")
    val diseasesInSalmon: String? = null,
    @SerialName("Displayed Seafood Profile Illustration")
    val displayedSeafoodProfileIllustration: String? = null,
    @SerialName("Ecosystem Services")
    val ecosystemServices: String? = null,
    @SerialName("Environmental Considerations")
    val environmentalConsiderations: String? = null,
    @SerialName("Environmental Effects")
    val environmentalEffects: String? = null,
    @SerialName("Farming Methods")
    val farmingMethods: String? = null,
    @SerialName("Farming Methods_")
    val farmingMethods_: String? = null,
    @SerialName("Fat, Total")
    val fatTotal: String? = null,
    @SerialName("Feeds")
    val feeds: String? = null,
    @SerialName("Feeds_")
    val feeds_: String? = null,
    @SerialName("Fiber, Total Dietary")
    val fiberTotalDietary: String? = null, // Agility gain per lvl
    @SerialName("Fishery Management")
    val fisheryManagement: String? = null, // Intelligence gain per lvl
    @SerialName("Fishing Rate")
    val fishingRate: String? = null,
    @SerialName("Habitat")
    val habitat: String? = null,
    @SerialName("Habitat Impacts")
    val habitatImpacts: String? = null,
    @SerialName("Harvest")
    val harvest: String? = null,
    @SerialName("Harvest Type")
    val harvestType: String? = null,
    @SerialName("Health Benefits")
    val healthBenefits: String? = null, // How many legs does this hero have?
    @SerialName("Human Health")
    val humanHealth: String? = null, // How many times picked for turbo matches?
    @SerialName("Human_Health_")
    val human_Health_: String? = null, // How many times won a turbo match?
//    @SerialName("Image Gallery")
//    val imageGallery: List<ImageGallery>,
    @SerialName("Location")
    val location: String? = null, // How many times picked in pro match?
    @SerialName("Management")
    val management: String? = null, // How many times picked first?
    @SerialName("NOAA Fisheries Region")
    val NOAAFisheriesRegion: String? = null, // How many times picked first and won?
    @SerialName("Path")
    val path: String? = null, // How many times picked second?
    @SerialName("Physical Description")
    val physicalDescription: String? = null, // How many times picked second and won?
    @SerialName("Population")
    val population: String? = null, // How many times picked third?
    @SerialName("Population Status")
    val populationStatus: String? = null, // How many times picked third and won?
    @SerialName("Production")
    val production: String? = null, // How many times picked in fourth round?
    @SerialName("Protein")
    val protein: String? = null, // How many times picked in fourth round and won?
    @SerialName("Quote")
    val quote: String? = null, // How many times picked fifth?
    @SerialName("Quote Background Color")
    val quoteBackgroundColor: String? = null, // How many times picked fifth and won?
    @SerialName("Research")
    val research: String? = null, // How many times picked sixth?
    @SerialName("Saturated Fatty Acids, Total")
    val saturatedFattyAcidsTotal: String? = null, // How many times picked sixth and won?
    @SerialName("Scientific Name")
    val scientificName: String? = null, // How many times picked seventh?
    @SerialName("Selenium")
    val selenium: String? = null, // How many times picked seventh and won?
    @SerialName("Serving Weight")
    val servingWeight: String? = null, // How many times picked eighth round?
    @SerialName("Servings")
    val servings: String? = null,
    @SerialName("Sodium")
    val sodium: String? = null,
    @SerialName("Source")
    val source: String? = null,
    @SerialName("Species Aliases")
    val speciesAliases: String? = null,
    @SerialName("Species Illustration Photo")
    val speciesIllustrationPhoto: SpeciesIllustrationPhotoDto? = null,
    @SerialName("Species Name")
    val speciesName: String? = null,
    @SerialName("Sugars, Total")
    val sugarsTotal: String? = null,
    @SerialName("Taste")
    val taste: String? = null,
    @SerialName("Texture")
    val texture: String? = null,
    @SerialName("last_update")
    val last_update: String? = null,
)

//@Serializable
//data class ImageGallery(
//    @SerialName("src")
//    val src: String? = null,
//    @SerialName("alt")
//    val alt: String? = null,
//    @SerialName("title")
//    val title: String? = null
//)

fun FishDto.toFish(): Fish {
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
        protein = protein,
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
        speciesIllustrationPhoto = speciesIllustrationPhoto?.toSpeciesIllustration(),
        speciesName = speciesName,
        sugarsTotal = sugarsTotal,
        taste = taste,
        texture = texture,
        last_update = last_update
    )
}
