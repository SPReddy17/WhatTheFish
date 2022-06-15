package com.example.fish_interactors

import com.example.core.DataState
import com.example.core.FilterOrder
import com.example.fish_domain.Fish
import com.example.fish_domain.FishFilter
import kotlinx.coroutines.flow.Flow

class FilterFishes {

    fun execute(
        current : List <Fish>,
        fishName : String,
        fishFilter: FishFilter,
    ) : List<Fish>{

        val filteredList :MutableList<Fish> = current.filter {
            it.speciesName?.lowercase()?.contains(fishName.lowercase()) ?:false
        }.toMutableList()

        when(fishFilter){
            is FishFilter.Fish ->{
                when(fishFilter.order){
                    is FilterOrder.Descending ->{
                        filteredList.sortByDescending { it.scientificName }
                    }
                    is FilterOrder.Ascending ->{
                         filteredList.sortBy { it.scientificName }
                    }
                }
            }
        }

        return filteredList
    }
}