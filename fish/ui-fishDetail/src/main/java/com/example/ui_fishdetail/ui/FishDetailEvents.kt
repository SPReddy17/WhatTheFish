package com.example.ui_fishdetail.ui

sealed class FishDetailEvents{


    data class GetFishFromCache(
        val scientificName : String,
    ):FishDetailEvents()

    object OnRemoveHeadFromQueue :FishDetailEvents()
}
