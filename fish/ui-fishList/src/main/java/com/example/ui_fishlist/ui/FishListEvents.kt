package com.example.ui_fishlist.ui

import com.example.fish_domain.FishFilter

sealed class FishListEvents {
    object GetFishes :FishListEvents()
    object FilterFishes :FishListEvents()
    data class UpdateFishName(
        val fishName :String,
    ) :FishListEvents()

    data class UpdateFishFilter(
        val fishFilter :FishFilter
    ):FishListEvents()
}