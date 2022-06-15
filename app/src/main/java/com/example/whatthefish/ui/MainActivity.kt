package com.example.whatthefish.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.example.fish_interactors.FishInteractors
import com.example.ui_fishdetail.ui.FishDetail
import com.example.ui_fishdetail.ui.FishDetailViewModel
import com.example.ui_fishlist.FishList
import com.example.ui_fishlist.ui.FishListViewModel
import com.example.whatthefish.ui.navigation.Screen
import com.example.whatthefish.ui.theme.WhatTheFishTheme
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

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
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.FishList.route,
                    builder = {
                        addFishList(
                            navController = navController,
                            imageLoader = imageLoader
                        )
                        addFishDetail(
                            imageLoader =imageLoader
                        )
                    }
                )
            }
        }
    }
}

fun NavGraphBuilder.addFishList(
    navController: NavController,
    imageLoader: ImageLoader
) {
    composable(
        route = Screen.FishList.route
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

fun NavGraphBuilder.addFishDetail(imageLoader: ImageLoader) {

    composable(
        route = Screen.FishDetail.route + "/{fishScientificName}",
        arguments = Screen.FishDetail.arguments,
    ) { navBackStackEntry ->
        val viewmodel :FishDetailViewModel = hiltViewModel()
        FishDetail(
            state = viewmodel.state.value,
            imageLoader = imageLoader
        )
    }
}