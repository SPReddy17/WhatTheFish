package com.example.ui_fishdetail.di

import com.example.fish_interactors.FishInteractors
import com.example.fish_interactors.GetFishFromCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 object FishDetailModule {

     @Provides
     @Singleton
     fun provideGetFishFromCache(
         interactors: FishInteractors
     ) : GetFishFromCache{
         return interactors.getFishFromCache
     }
}