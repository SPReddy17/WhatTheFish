package com.example.fish_datasource_test.network

sealed class FishServiceResponseType{
    object EmptyList : FishServiceResponseType()
    object Malformed : FishServiceResponseType()
    object GoodData : FishServiceResponseType()
    object Http404 : FishServiceResponseType()
}
