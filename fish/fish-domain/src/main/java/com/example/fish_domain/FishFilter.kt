package com.example.fish_domain

import com.example.core.FilterOrder

sealed class FishFilter(val uiValue :String){

    data class Fish(
        val order :FilterOrder  = FilterOrder.Descending
        ): FishFilter("Fish")

//    data class Fish(
//        val order :FilterOrder  = FilterOrder.Descending
//    ): FishFilter("Fish")
}
