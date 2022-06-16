package com.example.fish_datasource_test.cache

import com.example.fish_domain.Fish

class FishDatabaseFake {
    val fishes: MutableList<Fish> = mutableListOf()
}