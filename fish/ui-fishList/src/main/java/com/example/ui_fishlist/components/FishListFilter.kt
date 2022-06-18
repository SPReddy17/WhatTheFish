package com.example.ui_fishlist.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.core.FilterOrder
import com.example.fish_domain.FishFilter
import com.example.ui_fishlist.ui.test.TAG_FISH_FILTER_DIALOG
import com.example.ui_fishlist.ui.test.TAG_FISH_FILTER_DIALOG_DONE
import com.example.ui_fishlist.ui.test.TAG_FISH_FILTER_FISH_CHECKBOX

@ExperimentalAnimationApi
@Composable
fun FishListFilter(
    fishFilter: FishFilter,
    onUpdateFishFilter: (FishFilter) -> Unit,
    onCloseDialog: () -> Unit,
){
    AlertDialog(
        modifier = Modifier
            .padding(16.dp)
            .testTag(TAG_FISH_FILTER_DIALOG)
        ,
        onDismissRequest = {
            onCloseDialog()
        },
        title = {
            Text(
                text = "Filter",
                style = MaterialTheme.typography.h2,
            )
        },
        text = {
            LazyColumn {
                item{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){

                        // Spacer isn't working for some reason so use Row to create space
                        EmptyRow()

                        // Hero Filter
                        FishFilterSelector(
                            filterOnHero = {
                                onUpdateFishFilter(FishFilter.Fish())
                            },
                            isEnabled = fishFilter is FishFilter.Fish,
                            order = if(fishFilter is FishFilter.Fish) fishFilter.order else null,
                            orderDesc = {
                                onUpdateFishFilter(
                                    FishFilter.Fish(
                                        order = FilterOrder.Descending
                                    )
                                )
                            },
                            orderAsc = {
                                onUpdateFishFilter(
                                    FishFilter.Fish(
                                        order = FilterOrder.Ascending
                                    )
                                )
                            }
                        )
                    }
                }
            }
        },
        buttons = {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Row( // make the icon larger so it's easier to click
                    modifier = Modifier
                        .align(Alignment.End)
                        .testTag(TAG_FISH_FILTER_DIALOG_DONE)
                        .clickable {
                            onCloseDialog()
                        }
                    ,
                ){
                    Icon(
                        modifier = Modifier
                            .padding(10.dp)
                        ,
                        imageVector = Icons.Default.Check,
                        contentDescription = "Done",
                        tint = Color(0xFF009a34)
                    )
                }

            }
        }
    )
}

/**
 * @param filterOnHero: Set the HeroFilter to 'Hero'
 * @param isEnabled: Is the Hero filter the selected 'HeroFilter'
 * @param order: Ascending or Descending?
 * @param orderDesc: Set the order to descending.
 * @param orderAsc: Set the order to ascending.
 */
@ExperimentalAnimationApi
@Composable
fun FishFilterSelector(
    filterOnHero: () -> Unit,
    isEnabled: Boolean,
    order: FilterOrder? = null,
    orderDesc: () -> Unit,
    orderAsc: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .testTag(TAG_FISH_FILTER_FISH_CHECKBOX)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null, // disable the highlight
                    enabled = true,
                    onClick = {
                        filterOnHero()
                    },
                )
            ,
        ){
            Checkbox(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.CenterVertically)
                ,
                checked = isEnabled,
                onCheckedChange = {
                    filterOnHero()
                },
                colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
            )
            Text(
                text = FishFilter.Fish().uiValue,
                style = MaterialTheme.typography.h3,
            )
        }

        OrderSelector(
            descString = "z -> a",
            ascString = "a -> z",
            isEnabled = isEnabled,
            isDescSelected = isEnabled && order is FilterOrder.Descending,
            isAscSelected = isEnabled && order is FilterOrder.Ascending,
            onUpdateHeroFilterDesc = {
                orderDesc()
            },
            onUpdateHeroFilterAsc = {
                orderAsc()
            },
        )
    }
}