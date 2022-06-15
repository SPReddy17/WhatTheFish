package com.example.whatthefish.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class Screen(
    val route :String,
    val arguments : List<NamedNavArgument>
){

    object FishList :Screen(
        route = "fishList",
        arguments = emptyList()
    )

    object FishDetail :Screen(
        route = "fishDetail",
        arguments = listOf(
            navArgument("fishScientificName"){
                type = NavType.StringType
            }
        )
    )

}
