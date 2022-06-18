package com.example.ui_fishlist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import coil.ImageLoader
import com.example.fish_datasource_test.network.data.FishDataValid
import com.example.fish_datasource_test.network.serializeFishData
import com.example.ui_fishlist.coil.FakeImageLoader
import org.junit.Rule
import org.junit.Test

class FishListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val imageLoader: ImageLoader = FakeImageLoader.build(context = context)
    private val fishData = serializeFishData(FishDataValid.data)

    @OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
    @Test
    fun areFishesShown() {
        composeTestRule.setContent {
            val state = remember {
                FishListState(
                    fishes = fishData,
                    filteredFishes = fishData
                )
            }
            FishList(
                state = state,
                events = {},
                imageLoader = imageLoader,
                navigateToDetailScreen = {})
        }

         composeTestRule.onNodeWithText("Crimson Jobfish").assertIsDisplayed()
         composeTestRule.onNodeWithText("White Hake").assertIsDisplayed()
         composeTestRule.onNodeWithText("Atlantic Chub Mackerel").assertIsDisplayed()
    }
}