package com.example.ui_fishdetail.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import coil.ImageLoader
import com.example.fish_datasource_test.network.data.FishDataValid
import com.example.fish_datasource_test.network.serializeFishData
import com.example.ui_fishdetail.coil.FakeImageLoader
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

@ExperimentalAnimationApi
@ExperimentalComposeApi
class FishDetailTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val imageLoader: ImageLoader = FakeImageLoader.build(context = context)
    private val fishData = serializeFishData(FishDataValid.data)

    @Test
    fun isFishDataShown() {
        //choose a random fish
        val fish = fishData.get(Random.nextInt(0, fishData.size - 1))
        composeTestRule.setContent {
            val state = remember {
                FishDetailState(
                    fish = fish
                )
            }
            FishDetail(state = state, imageLoader = imageLoader, events = {})

        }
        composeTestRule.onNodeWithText(fish.speciesName ?: "").assertIsDisplayed()
    }
}