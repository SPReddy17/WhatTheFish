package com.example.ui_fishlist.di

import com.example.core.Logger
import com.example.fish_interactors.FilterFishes
import com.example.fish_interactors.FishInteractors
import com.example.fish_interactors.GetFishes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.logging.Filter
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FishListModule {

    @Provides
    @Singleton
    fun provideGetFishes(
        interactors: FishInteractors
    ): GetFishes {
        return interactors.getFishes
    }

    @Provides
    @Singleton
    fun provideFilterFishes(
        interactors: FishInteractors
    ): FilterFishes  {
        return interactors.filterFishes
    }

    @Provides
    @Singleton
    @Named("fishListLogger")
    fun provideLogger(): Logger {
        return Logger(
            tag = "FishList",
            isDebug = true
        )
    }

}