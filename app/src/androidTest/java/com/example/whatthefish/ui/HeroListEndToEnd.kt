package com.example.whatthefish.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import coil.ImageLoader
import com.example.fish_datasource.cache.FishCache
import com.example.fish_datasource.network.FishService
import com.example.fish_datasource_test.cache.FishCacheFake
import com.example.fish_datasource_test.cache.FishDatabaseFake
import com.example.fish_datasource_test.network.FishServiceFake
import com.example.fish_datasource_test.network.FishServiceResponseType
import com.example.fish_interactors.FilterFishes
import com.example.fish_interactors.FishInteractors
import com.example.fish_interactors.GetFishFromCache
import com.example.fish_interactors.GetFishes
import com.example.ui_fishdetail.ui.FishDetail
import com.example.ui_fishdetail.ui.FishDetailViewModel
import com.example.ui_fishlist.ui.FishList
import com.example.ui_fishlist.ui.FishListViewModel
import com.example.ui_fishlist.ui.test.*
import com.example.whatthefish.coil.FakeImageLoader
import com.example.whatthefish.di.FishInteractorsModule
import com.example.whatthefish.ui.navigation.Screen
import com.example.whatthefish.ui.theme.WhatTheFishTheme
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton


@UninstallModules(FishInteractorsModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HeroListEndToEnd {

    @Module
    @InstallIn(SingletonComponent::class)
    object TestFishInteractorsModule {
        @Provides
        @Singleton
        fun provideFishCache(): FishCache {
            return FishCacheFake(FishDatabaseFake())
        }

        @Provides
        @Singleton
        fun provideFishService(): FishService {
            return FishServiceFake.build(
                type = FishServiceResponseType.GoodData
            )
        }

        @Provides
        @Singleton
        fun provideFishInteractors(
            cache: FishCache,
            service: FishService,
        ): FishInteractors {
            return FishInteractors(
                getFishes = GetFishes(
                    cache = cache,
                    service = service
                ),
                getFishFromCache = GetFishFromCache(cache = cache),
                filterFishes = FilterFishes()
            )
        }
    }

    @get:Rule()
    var hiltRule = HiltAndroidRule(this)

    @OptIn(ExperimentalComposeUiApi::class)
    @get:Rule()
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val imageLoader: ImageLoader = FakeImageLoader.build(context)

    @OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
    @Before
    fun before() {
        composeTestRule.setContent {
            WhatTheFishTheme() {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.FishList.route,
                    builder = {
                        composable(
                            route = Screen.FishList.route,
                        ) {
                            val viewModel: FishListViewModel = hiltViewModel()
                            FishList(
                                state = viewModel.state.value,
                                events = viewModel::onTriggerEvent,
                                navigateToDetailScreen = { scientificName ->
                                    navController.navigate("${Screen.FishDetail.route}/$scientificName")
                                },
                                imageLoader = imageLoader,
                            )
                        }
                        composable(
                            route = Screen.FishDetail.route + "/{scientificName}",
                            arguments = Screen.FishDetail.arguments,
                        ) {
                            val viewModel: FishDetailViewModel = hiltViewModel()
                            FishDetail(
                                state = viewModel.state.value,
                                events = viewModel::onTriggerEvent,
                                imageLoader = imageLoader
                            )
                        }
                    }
                )
            }
        }
    }

    @Test
    fun testSearchFishByName() {
        composeTestRule.onRoot(useUnmergedTree = true)
            .printToLog("TAG") // For learning the ui tree system

        composeTestRule.onNodeWithTag(TAG_FISH_SEARCH_BAR).performTextInput("White Hake")
        composeTestRule.onNodeWithTag(TAG_FISH_SPECIES_NAME, useUnmergedTree = true)
            .assertTextEquals(
                "White Hake",
            )
        composeTestRule.onNodeWithTag(TAG_FISH_SEARCH_BAR).performTextClearance()

        composeTestRule.onNodeWithTag(TAG_FISH_SEARCH_BAR).performTextInput("Red Hake")
        composeTestRule.onNodeWithTag(TAG_FISH_SPECIES_NAME, useUnmergedTree = true)
            .assertTextEquals(
                "Red Hake",
            )
        composeTestRule.onNodeWithTag(TAG_FISH_SEARCH_BAR).performTextClearance()

        composeTestRule.onNodeWithTag(TAG_FISH_SEARCH_BAR).performTextInput("Pacific Wahoo")
        composeTestRule.onNodeWithTag(TAG_FISH_SPECIES_NAME, useUnmergedTree = true)
            .assertTextEquals(
                "Pacific Wahoo",
            )
    }

    @Test
    fun testFilterFishAlphabetically() {
        // Show the dialog
        composeTestRule.onNodeWithTag(TAG_FISH_FILTER_BTN).performClick()

        // Confirm the filter dialog is showing
        composeTestRule.onNodeWithTag(TAG_FISH_FILTER_DIALOG).assertIsDisplayed()

        // Filter by "Hero" name
        composeTestRule.onNodeWithTag(TAG_FISH_FILTER_FISH_CHECKBOX).performClick()

        // Order Descending (z-a)
        composeTestRule.onNodeWithTag(TAG_FISH_FILTER_DESC).performClick()

        // Close the dialog
        composeTestRule.onNodeWithTag(TAG_FISH_FILTER_DIALOG_DONE).performClick()

        // Confirm the order is correct
        composeTestRule.onAllNodesWithTag(TAG_FISH_SPECIES_NAME, useUnmergedTree = true)
            .assertAny(hasText("Pacific Bluefin Tuna"))

        // Show the dialog
        composeTestRule.onNodeWithTag(TAG_FISH_FILTER_BTN).performClick()

        // Order Ascending (a-z)
        composeTestRule.onNodeWithTag(TAG_FISH_FILTER_ASC).performClick()

        // Close the dialog
        composeTestRule.onNodeWithTag(TAG_FISH_FILTER_DIALOG_DONE).performClick()

        // Confirm the order is correct
        composeTestRule.onAllNodesWithTag(TAG_FISH_SPECIES_NAME, useUnmergedTree = true)
            .assertAny(hasText("Pacific Wahoo"))
    }
}