package com.example.ui_fishlist.ui

sealed class FishListEvents {
    object GetFishes :FishListEvents()
    object FilterFishes :FishListEvents()
    data class UpdateFishName(
        val fishName :String,
    ) :FishListEvents()
}