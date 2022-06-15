package com.example.whatthefish.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.ImageLoader
import com.example.fish_interactors.FishInteractors
import com.example.ui_fishdetail.ui.FishDetail
import com.example.ui_fishdetail.ui.FishDetailViewModel
import com.example.ui_fishlist.ui.FishList
import com.example.ui_fishlist.ui.FishListViewModel
import com.example.whatthefish.ui.navigation.Screen
import com.example.whatthefish.ui.theme.WhatTheFishTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val getFishes = FishInteractors.build(
            sqlDriver = AndroidSqliteDriver(
                schema = FishInteractors.schema,
                context = this,
                name = FishInteractors.dbName
            )
        ).getFishes

        setContent {
            WhatTheFishTheme {
                val navController = rememberAnimatedNavController()

                BoxWithConstraints(
                ) {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.FishList.route,
                        builder = {
                            addFishList(
                                navController = navController,
                                imageLoader = imageLoader,
                                width = constraints.maxWidth/2
                            )
                            addFishDetail(
                                imageLoader = imageLoader,
                                width = constraints.maxWidth/2
                            )
                        }
                    )
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addFishList(
    navController: NavController,
    imageLoader: ImageLoader,
    width :Int,
) {
    composable(
        route = Screen.FishList.route,
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                )
            ) + fadeIn(animationSpec = tween(300))
        }
    ) {
        val viewModel: FishListViewModel = hiltViewModel()
        FishList(
            state = viewModel.state.value,
            events = viewModel::onTriggerEvent,
            imageLoader = imageLoader,
            navigateToDetailScreen = { fishScientificName ->
                navController.navigate(
                    route = "${Screen.FishDetail.route}/$fishScientificName"
                )
            }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addFishDetail(imageLoader: ImageLoader, width: Int) {

    composable(
        route = Screen.FishDetail.route + "/{fishScientificName}",
        arguments = Screen.FishDetail.arguments,
        enterTransition = {_,_ ->
            slideInHorizontally(
                initialOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))

        },
        popExitTransition = {_,_ ->
            slideOutHorizontally(
                targetOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ) { navBackStackEntry ->
        val viewmodel: FishDetailViewModel = hiltViewModel()
        FishDetail(
            state = viewmodel.state.value,
            imageLoader = imageLoader,
            events = viewmodel::onTriggerEvent
        )
    }
}