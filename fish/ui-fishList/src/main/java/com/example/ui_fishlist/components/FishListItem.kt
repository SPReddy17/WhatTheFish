package com.example.ui_fishlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.fish_domain.Fish
import com.example.ui_fishlist.R
import com.example.ui_fishlist.ui.test.TAG_FISH_SCIENTIFIC_NAME
import com.example.ui_fishlist.ui.test.TAG_FISH_SPECIES_NAME

@Composable
fun FishListItem(
    fish: Fish,
    onSelectFish: (String) -> Unit,
    imageLoader: ImageLoader,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(MaterialTheme.colors.surface)
            .clickable {
                fish.scientificName?.let { onSelectFish(it) }
            },
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painter = rememberImagePainter(
                fish.speciesIllustrationPhoto?.src,
                imageLoader = imageLoader,
                builder = {
                    placeholder(
                        if (isSystemInDarkTheme()) {
                            R.drawable.black_background
                        } else {
                            R.drawable.white_background
                        }
                    )
                }
            )
            Image(
                // TODO(Replace with Image)
                modifier = Modifier
                    .width(120.dp)
                    .height(70.dp)
                    .background(Color.LightGray),
                painter = painter,
                contentDescription = fish.speciesName,
                contentScale = ContentScale.Fit

            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(.8f) // fill 80% of remaining width
                    .padding(start = 12.dp)
            ) {
                fish.speciesName?.let {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .testTag(TAG_FISH_SPECIES_NAME),
                        text = it,
                        style = MaterialTheme.typography.h4,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                fish.scientificName?.let {
                    Text(
                        modifier = Modifier
                            .testTag(TAG_FISH_SCIENTIFIC_NAME),
                        text = "Scientific Name : $it",
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth() // Fill the rest of the width (100% - 80% = 20%)
                    .padding(end = 12.dp),
                horizontalAlignment = Alignment.End
            ) {
                // Using remember in list item does not behave correctly?
//                val proWR: Int = remember{round(hero.proWins.toDouble() / hero.proPick.toDouble() * 100).toInt()}
//                val proWR: Int = round(fish.proWins.toDouble() / fish.proPick.toDouble() * 100).toInt()
                Text(
                    text = "${fish.protein}",
                    style = MaterialTheme.typography.caption,
                    // color = if(proWR > 50) Color(0xFF009a34) else MaterialTheme.colors.error,
                )
            }
        }
    }
}